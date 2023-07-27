package com.odeyalo.sonata.artists.service.verification;

import com.odeyalo.sonata.suite.brokers.events.artist.registration.ArtistRegistrationVerificationRequiredEvent;
import reactor.core.publisher.Mono;

public interface ArtistRegistrationVerificationRequiredHandler {

    Mono<Void> handleVerificationRequired(ArtistRegistrationVerificationRequiredEvent event);

}
