package com.odeyalo.sonata.artists.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * Default event source that provide ability to add events to stream
 */
@Component
public class DefaultEventSource implements EmittingEventSource {
    Sinks.Many<SonataEvent> sink =  Sinks.many()
            .multicast()
            .onBackpressureBuffer();

    @Override
    public Mono<Void> emitNext(SonataEvent event) {
        return Mono.just(sink.tryEmitNext(event))
                .then();
    }

    @Override
    public Flux<? extends SonataEvent> getEvents() {
        return sink.asFlux();
    }
}
