package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageDeletedEvent(BasePayload basePayload, DeletedMessage message) {
    public MessageDeletedEvent(@JsonProperty("eventTime") String eventTime,
                               @JsonProperty("message") DeletedMessage message) {
        this(new BasePayload(eventTime), message);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DeletedMessage(String id, String channelId) {
        public DeletedMessage(@JsonProperty("id") String id,
                              @JsonProperty("channelId") String channelId) {
            this.id = id;
            this.channelId = channelId;
        }
    }
}
