package com.odeyalo.sonata.artists.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Support {@link EventPublisher} that just delegates all the job to {@link EventPublisherDelegate}
 * and used as wrapper class only
 * @param <T>
 */
public class EventPublisherSupport<T extends SonataEvent> implements EventPublisher<T> {
    protected final EventPublisherDelegate publisherDelegate;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EventPublisherSupport(EventPublisherDelegate publisherDelegate) {
        this.publisherDelegate = publisherDelegate;
        this.logger.debug("Using the: {} for event publishing", publisherDelegate);
    }

    @Override
    public Mono<Void> publishEvent(T event) {
        return publisherDelegate.publishEvent(event);
    }
}
