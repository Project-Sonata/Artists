package com.odeyalo.sonata.artists.service.event.listener;

import com.odeyalo.sonata.artists.service.event.EventSource;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class ArtistRegistrationConfirmationRequiredEventListener implements EventListener {
    final EventSource eventSource;

    public ArtistRegistrationConfirmationRequiredEventListener(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @PostConstruct
    public void initialize() {
        eventSource.getEvents()
                .subscribeOn(Schedulers.parallel())
                .flatMap(this::handleEvent)
                .next()
                .subscribe();
    }

    @Override
    public Mono<Void> onEvent(SonataEvent event) {
        return handleEvent(event);
    }

    private Mono<Void> handleEvent(SonataEvent event) {
        return Mono.fromRunnable(() -> {
            System.out.println("handle the from event listener: " + event);
        });
    }
}
