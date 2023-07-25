package com.odeyalo.sonata.artists.service.event;

import com.testing.MockEvent;
import com.testing.TestingEventListenerSpy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InternalEventPublisherDelegateTest {
    DefaultEventSource eventSource = new DefaultEventSource();

    InternalEventPublisherDelegate publisherDelegate = new InternalEventPublisherDelegate(eventSource);

    @Test
    void publishEvent() {

        TestingEventListenerSpy spy = new TestingEventListenerSpy(eventSource);

        publisherDelegate.publishEvent(new MockEvent()).block();

        assertTrue(spy.wasInvoked(), "The event must be invoked!");
    }
}
