package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Channel(String id,
                      String name,
                      String path,
                      String parentId,
                      User creator,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
    public Channel(@JsonProperty("id") String id,
                   @JsonProperty("name") String name,
                   @JsonProperty("path") String path,
                   @JsonProperty("parentId") String parentId,
                   @JsonProperty("creator") User creator,
                   @JsonProperty("createdAt") String createdAt,
                   @JsonProperty("updatedAt") String updatedAt) {
        this(
                id, name, path, parentId, creator,
                Timestamp.valueOf(createdAt.replace("T", " ").replace("Z", "")).toLocalDateTime(),
                Timestamp.valueOf(updatedAt.replace("T", " ").replace("Z", "")).toLocalDateTime()
        );
    }
}
