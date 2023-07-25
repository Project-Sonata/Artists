package com.odeyalo.sonata.artists.service.event.listener;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Mono;

/**
 * Listen to SonataEvent
 */
public interface EventListener {

    Mono<Void> onEvent(SonataEvent event);

}
