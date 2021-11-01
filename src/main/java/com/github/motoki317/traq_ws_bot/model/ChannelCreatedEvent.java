package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChannelCreatedEvent(BasePayload basePayload, Channel channel) {
    public ChannelCreatedEvent(@JsonProperty("eventTime") String eventTime,
                               @JsonProperty("channel") Channel channel) {
        this(new BasePayload(eventTime), channel);
    }
}
