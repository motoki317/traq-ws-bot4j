package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DirectMessageDeletedEvent(BasePayload basePayload, Message message) {
    public DirectMessageDeletedEvent(@JsonProperty("eventTime") String eventTime,
                                     @JsonProperty("message") Message message) {
        this(new BasePayload(eventTime), message);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DeletedMessage(String id, String userId, String channelId) {
        public DeletedMessage(@JsonProperty("id") String id,
                              @JsonProperty("userId") String userId,
                              @JsonProperty("channelId") String channelId) {
            this.id = id;
            this.userId = userId;
            this.channelId = channelId;
        }
    }
}
