package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Embedded(String raw, String type, String id) {
    public Embedded(@JsonProperty("raw") String raw,
                    @JsonProperty("type") String type,
                    @JsonProperty("id") String id) {
        this.raw = raw;
        this.type = type;
        this.id = id;
    }
}
