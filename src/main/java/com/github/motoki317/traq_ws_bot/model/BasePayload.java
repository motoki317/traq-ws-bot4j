package com.github.motoki317.traq_ws_bot.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record BasePayload(LocalDateTime eventTime) {
    public BasePayload(String eventTime) {
        this(Timestamp.valueOf(eventTime.replace("T", " ").replace("Z", "")).toLocalDateTime());
    }
}
