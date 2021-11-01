package com.github.motoki317.traq_ws_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(String id, String name, String displayName, String iconId,
                   boolean bot) {
    public User(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("displayName") String displayName,
                @JsonProperty("iconId") String iconId,
                @JsonProperty("bot") boolean bot) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.iconId = iconId;
        this.bot = bot;
    }
}
