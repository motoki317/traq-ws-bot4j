package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StampCreatedEvent(BasePayload basePayload,
                                String id,
                                String name,
                                String fileId,
                                User creator) {
    public StampCreatedEvent(@JsonProperty("eventTime") String eventTime,
                             @JsonProperty("id") String id,
                             @JsonProperty("name") String name,
                             @JsonProperty("fileId") String fileId,
                             @JsonProperty("creator") User creator) {
        this(new BasePayload(eventTime), id, name, fileId, creator);
    }
}
