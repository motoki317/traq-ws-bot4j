package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JoinedEvent(BasePayload basePayload, Channel channel) {
    public JoinedEvent(@JsonProperty("eventTime") String eventTime,
                       @JsonProperty("channel") Channel channel) {
        this(new BasePayload(eventTime), channel);
    }
}
