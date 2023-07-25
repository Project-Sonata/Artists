package com.odeyalo.sonata.artists.service.event.impl;

import com.odeyalo.sonata.artists.service.event.EventPublisherSupport;
import com.odeyalo.sonata.artists.service.event.EventPublisherDelegate;
import com.odeyalo.sonata.suite.brokers.events.artist.registration.ArtistRegistrationVerificationRequiredEvent;
import org.springframework.stereotype.Component;

/**
 * Publish only the {@link ArtistRegistrationVerificationRequiredEvent}
 */
@Component
public class ArtistRegistrationConfirmationRequiredEventPublisher
        extends EventPublisherSupport<ArtistRegistrationVerificationRequiredEvent> {

    public ArtistRegistrationConfirmationRequiredEventPublisher(EventPublisherDelegate delegate) {
        super(delegate);
    }
}