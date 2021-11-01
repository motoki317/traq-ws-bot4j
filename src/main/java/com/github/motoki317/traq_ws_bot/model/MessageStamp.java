package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageStamp(String stampId,
                           String userId,
                           int count,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
    public MessageStamp(@JsonProperty("stampId") String stampId,
                        @JsonProperty("userId") String userId,
                        @JsonProperty("count") int count,
                        @JsonProperty("createdAt") String createdAt,
                        @JsonProperty("updatedAt") String updatedAt) {
        this(
                stampId, userId, count,
                Timestamp.valueOf(createdAt.replace("T", " ").replace("Z", "")).toLocalDateTime(),
                Timestamp.valueOf(updatedAt.replace("T", " ").replace("Z", "")).toLocalDateTime()
        );
    }
}
