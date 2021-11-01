package com.github.motoki317.traq_ws_bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.motoki317.traq4j.ApiClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Bot {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final long FIRST_RETRY_WAIT = Duration.ofSeconds(3).toMillis();
    private static final long MAX_RETRY_WAIT = Duration.ofMinutes(10).toMillis();

    private final ApiClient client;
    private final WebSocketAdapter ws;
    private final boolean autoReconnect;

    private final Map<String, List<Consumer<JsonNode>>> handlers;

    private long nextRetryWait;

    /**
     * Creates a new traQ bot server.
     * @param accessToken Access token to fetch traQ API endpoints with.
     * @param traqApiBasePath If given, sets api base path.
     * @param autoReconnect If the bot should auto reconnect if disconnected from WebSocket.
     */
    public Bot(@Nonnull String accessToken, @Nullable String traqApiBasePath, boolean autoReconnect) {
        this.client = new ApiClient();
        this.client.addDefaultHeader("Authorization", "Bearer " + accessToken);

        if (traqApiBasePath != null) {
            this.client.setBasePath(traqApiBasePath);
        }

        // actual threads to handle the events
        var executors = Executors.newFixedThreadPool(5);

        this.handlers = new HashMap<>();
        this.ws = new WebSocketAdapter(accessToken, this.client.getBasePath(), (type, body) -> {
            for (Consumer<JsonNode> h : this.handlers.getOrDefault(type, List.of())) {
                executors.submit(() -> h.accept(body));
            }
        });
        this.autoReconnect = autoReconnect;
        this.nextRetryWait = FIRST_RETRY_WAIT;
    }

    /**
     * Returns traQ API client for use of other endpoints.
     * @return API client.
     */
    public ApiClient getClient() {
        return client;
    }

    /**
     * Connect to WebSocket and start receiving events.
     * Blocks on success.
     */
    @SuppressWarnings("LoopConditionNotUpdatedInsideLoop")
    public void start() {
        do {
            boolean errored = false;
            try {
                this.ws.start().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                errored = true;
            }

            if (errored) {
                System.out.printf("[traq-ws-bot] Failed to connect to %s, retrying in %d ms ...\n", this.ws.getConnectPath(), this.nextRetryWait);
                try {
                    Thread.sleep(this.nextRetryWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // exponential backoff
                this.nextRetryWait = Math.min(this.nextRetryWait * 2, MAX_RETRY_WAIT);
            } else {
                // once connected, but disconnected for some reason
                this.nextRetryWait = FIRST_RETRY_WAIT;
                System.out.printf("[traq-ws-bot4j] Disconnected from WebSocket, retrying in %d ms ...\n", this.nextRetryWait);
                try {
                    Thread.sleep(this.nextRetryWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (this.autoReconnect);
    }

    /**
     * Sends command to the WebSocket connection.
     * @param cmd Command to send.
     */
    public void sendCommand(String cmd) {
        this.ws.sendCommand(cmd);
    }

    public void sendWebRTCState(String channelId, WebRTCState ...states) {
        if (states.length == 0) {
            this.sendCommand(String.format("rtcstate:%s:", channelId));
            return;
        }

        List<String> elems = new ArrayList<>(2 + 2 * states.length);
        elems.add("rtcstate");
        elems.add(channelId);
        for (WebRTCState state : states) {
            elems.add(state.state());
            elems.add(state.sessionId());
        }
        this.sendCommand(String.join(":", elems) + ":");
    }

    private void addHandler(String type, Consumer<JsonNode> h) {
        if (!this.handlers.containsKey(type)) this.handlers.put(type, new ArrayList<>());
        this.handlers.get(type).add(h);
    }

    /**
     * Listen on a specific event.
     * @param event Event name.
     * @param c Event record class.
     * @param h Handler.
     * @param <T> Event record class. Should be the same as c.
     */
    public <T> void onEvent(Event event, Class<T> c, Consumer<T> h) {
        this.addHandler(event.toString(), (body) -> {
            try {
                h.accept(mapper.readValue(body.toString(), c));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
