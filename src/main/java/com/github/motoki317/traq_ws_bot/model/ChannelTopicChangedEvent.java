package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChannelTopicChangedEvent(BasePayload basePayload,
                                       Channel channel,
                                       String topic,
                                       User updater) {
    public ChannelTopicChangedEvent(@JsonProperty("eventTime") String eventTime,
                                    @JsonProperty("channel") Channel channel,
                                    @JsonProperty("topic") String topic,
                                    @JsonProperty("user") User updater) {
        this(new BasePayload(eventTime), channel, topic, updater);
    }
}
