package com.odeyalo.sonata.artists.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Flux;

/**
 * The {@code EventSource} interface represents a source of events.
 * It provides a reactive stream of events that can be subscribed to by event listeners.
 */
public interface EventSource {

    Flux<? extends SonataEvent> getEvents();

}
