package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(String id,
                      User user,
                      String channelId,
                      String text,
                      String plainText,
                      Embedded[] embedded,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
    public Message(@JsonProperty("id") String id,
                   @JsonProperty("user") User user,
                   @JsonProperty("channelId") String channelId,
                   @JsonProperty("text") String text,
                   @JsonProperty("plainText") String plainText,
                   @JsonProperty("embedded") Embedded[] embedded,
                   @JsonProperty("createdAt") String createdAt,
                   @JsonProperty("updatedAt") String updatedAt) {
        this(
                id, user, channelId, text, plainText, embedded,
                Timestamp.valueOf(createdAt.replace("T", " ").replace("Z", "")).toLocalDateTime(),
                Timestamp.valueOf(updatedAt.replace("T", " ").replace("Z", "")).toLocalDateTime()
        );
    }
}
