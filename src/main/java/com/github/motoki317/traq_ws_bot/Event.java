package com.github.motoki317.traq_ws_bot;

import com.github.motoki317.traq_ws_bot.model.*;

public enum Event {
    PING(PingEvent.class),
    JOINED(JoinedEvent.class),
    LEFT(LeftEvent.class),
    MESSAGE_CREATED(MessageCreatedEvent.class),
    MESSAGE_UPDATED(MessageUpdatedEvent.class),
    MESSAGE_DELETED(MessageDeletedEvent.class),
    BOT_MESSAGE_STAMPS_UPDATED(BotMessageStampsUpdatedEvent.class),
    DIRECT_MESSAGE_CREATED(DirectMessageCreatedEvent.class),
    DIRECT_MESSAGE_UPDATED(DirectMessageUpdatedEvent.class),
    DIRECT_MESSAGE_DELETED(DirectMessageDeletedEvent.class),
    CHANNEL_CREATED(ChannelCreatedEvent.class),
    CHANNEL_TOPIC_CHANGED(ChannelTopicChangedEvent.class),
    USER_CREATED(UserCreatedEvent.class),
    STAMP_CREATED(StampCreatedEvent.class),
    TAG_ADDED(TagAddedEvent.class),
    TAG_REMOVED(TagRemovedEvent.class);

    private final Class<?> payloadClass;

    Event(Class<?> payloadClass) {
        this.payloadClass = payloadClass;
    }

    public Class<?> getPayloadClass() {
        return payloadClass;
    }
}
