package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DirectMessageCreatedEvent(BasePayload basePayload, Message message) {
    public DirectMessageCreatedEvent(@JsonProperty("eventTime") String eventTime,
                                     @JsonProperty("message") Message message) {
        this (new BasePayload(eventTime), message);
    }
}
