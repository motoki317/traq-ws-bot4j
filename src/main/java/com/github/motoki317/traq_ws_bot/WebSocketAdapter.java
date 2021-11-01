package com.github.motoki317.traq_ws_bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

class WebSocketAdapter {
    private static final String TRAQ_GATEWAY_PATH = "/bots/ws";
    private static final ObjectMapper mapper = new ObjectMapper();

    private final String accessToken;
    private final String traqApiBasePath;
    private final BiConsumer<String, JsonNode> callback;

    private volatile WebSocket ws;
    private volatile boolean connected;

    public WebSocketAdapter(@Nonnull String accessToken, @Nonnull String traqApiBasePath, BiConsumer<String, JsonNode> callback) {
        this.accessToken = accessToken;
        this.traqApiBasePath = traqApiBasePath
                .replace("https://", "wss://")
                .replace("http://", "ws://");
        this.callback = callback;
    }

    public String getConnectPath() {
        return this.traqApiBasePath + TRAQ_GATEWAY_PATH;
    }

    /**
     * Connect to WebSocket and start receiving events.
     * This method should be safe to be called multiple times, once the earlier connection finishes.
     */
    public synchronized CompletableFuture<Void> start() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> closed = new CompletableFuture<>();

        WebSocketAdapter wsa = this;
        WebSocket.Listener listener = new WebSocket.Listener() {
            private StringBuilder textSB = new StringBuilder();

            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("[traq-ws-bot4j] Connected!");
                wsa.connected = true;
                WebSocket.Listener.super.onOpen(webSocket);
            }

            @Override
            public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                closed.complete(null);
                System.out.println("[traq-ws-bot4j] Closing connection...");
                wsa.connected = false;
                wsa.ws = null;
                return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                textSB.append(data);
                if (last) {
                    String fullData = textSB.toString();
                    textSB = new StringBuilder();
                    try {
                        JsonNode obj = mapper.readTree(fullData);
                        String type = obj.get("type").asText();
                        JsonNode body = obj.get("body");
                        wsa.callback.accept(type, body);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                return WebSocket.Listener.super.onText(webSocket, data, last);
            }

            @Override
            public void onError(WebSocket webSocket, Throwable error) {
                error.printStackTrace();
                WebSocket.Listener.super.onError(webSocket, error);
            }
        };

        WebSocket.Builder wsb = HttpClient
                .newHttpClient()
                .newWebSocketBuilder();
        wsb.header("Authorization", "Bearer " + this.accessToken);
        this.ws = wsb.buildAsync(URI.create(this.getConnectPath()), listener).get();
        return closed;
    }

    public synchronized void sendCommand(String cmd) {
        if (!this.connected) {
            System.out.printf("[traq-ws-bot4j] Warning: discarded command because not connected: %s\n", cmd);
            return;
        }
        this.ws.sendText(cmd, true);
    }
}
