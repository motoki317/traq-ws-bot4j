package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BotMessageStampsUpdatedEvent(BasePayload basePayload,
                                           String messageId,
                                           List<MessageStamp> stamps) {
    public BotMessageStampsUpdatedEvent(@JsonProperty("eventTime") String eventTime,
                                        @JsonProperty("messageId") String messageId,
                                        @JsonProperty("stamps") List<MessageStamp> stamps) {
        this(new BasePayload(eventTime), messageId, stamps);
    }
}
