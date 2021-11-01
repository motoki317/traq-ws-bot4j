package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PingEvent(BasePayload basePayload) {
    public PingEvent(@JsonProperty("eventTime") String eventTime) {
        this(new BasePayload(eventTime));
    }
}
