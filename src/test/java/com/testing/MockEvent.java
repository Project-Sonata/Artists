package com.testing;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;

import java.util.UUID;

public class MockEvent implements SonataEvent {
    private final String id;
    private final long creationTime;

    public MockEvent(String id, long creationTime) {
        this.id = id;
        this.creationTime = creationTime;
    }

    public MockEvent(String id) {
        this.id = id;
        this.creationTime = System.currentTimeMillis();
    }

    public MockEvent() {
        this.id = UUID.randomUUID().toString();
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public long creationTime() {
        return creationTime;
    }
}
