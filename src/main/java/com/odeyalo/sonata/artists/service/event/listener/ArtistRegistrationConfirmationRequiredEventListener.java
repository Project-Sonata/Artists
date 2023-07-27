package com.odeyalo.sonata.artists.service.event.listener;

import com.odeyalo.sonata.artists.service.event.EventSource;
import com.odeyalo.sonata.artists.service.verification.ArtistRegistrationVerificationRequiredHandler;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.artist.registration.ArtistRegistrationVerificationRequiredEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class ArtistRegistrationConfirmationRequiredEventListener implements EventListener {
    final EventSource eventSource;
    final ArtistRegistrationVerificationRequiredHandler handler;

    public ArtistRegistrationConfirmationRequiredEventListener(EventSource eventSource, ArtistRegistrationVerificationRequiredHandler handler) {
        this.eventSource = eventSource;
        this.handler = handler;
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
        if (!(event instanceof ArtistRegistrationVerificationRequiredEvent verificationRequiredEvent)) {
            return Mono.empty();
        }
        return handler.handleVerificationRequired(verificationRequiredEvent);
    }
}
