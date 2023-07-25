package com.testing;

import com.odeyalo.sonata.artists.service.event.EventSource;
import com.odeyalo.sonata.artists.service.event.listener.EventListener;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Mono;

public class TestingEventListenerSpy implements EventListener {
    private boolean wasInvoked = false;

    public TestingEventListenerSpy(EventSource source) {
        source.getEvents()
                .flatMap(this::onEvent)
                .subscribe();
    }

    @Override
    public Mono<Void> onEvent(SonataEvent event) {
        wasInvoked = true;
        return Mono.empty();
    }

    public boolean wasInvoked() {
        return wasInvoked;
    }
}
