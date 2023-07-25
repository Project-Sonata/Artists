package com.odeyalo.sonata.artists.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * EventPublisherDelegate that used to publish events only in the microservice and does not send any events to message brokers.
 */
@Component
public class InternalEventPublisherDelegate implements EventPublisherDelegate {
    final EmittingEventSource eventSource;

    public InternalEventPublisherDelegate(EmittingEventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public Mono<Void> publishEvent(SonataEvent event) {
        System.out.println("Sending the event: " + event);
        return eventSource.emitNext(event);
    }
}

