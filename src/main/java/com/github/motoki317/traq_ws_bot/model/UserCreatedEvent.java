package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserCreatedEvent(BasePayload basePayload, User user) {
    public UserCreatedEvent(@JsonProperty("eventTime") String eventTime,
                            @JsonProperty("user") User user) {
        this(new BasePayload(eventTime), user);
    }
}
