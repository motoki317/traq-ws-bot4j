package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagRemovedEvent(BasePayload basePayload, String tagId, String tag) {
    public TagRemovedEvent(@JsonProperty("eventTime") String eventTime,
                           @JsonProperty("tagId") String tagId,
                           @JsonProperty("tag") String tag) {
        this(new BasePayload(eventTime), tagId, tag);
    }
}
