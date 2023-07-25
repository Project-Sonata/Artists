package com.odeyalo.sonata.artists.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Mono;

/**
 * The {@code EventPublisherDelegate} class is an intermediary between specific event publishers and a general event
 * source. It acts as a delegate for individual event publishers, forwarding their events to the event source for
 * central processing and distribution to subscribers.
 */
public interface EventPublisherDelegate {

    Mono<Void> publishEvent(SonataEvent event);

}
