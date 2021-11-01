package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageUpdatedEvent(BasePayload basePayload, Message message) {
    public MessageUpdatedEvent(@JsonProperty("eventTime") String eventTime,
                               @JsonProperty("message") Message message) {
        this(new BasePayload(eventTime), message);
    }
}
