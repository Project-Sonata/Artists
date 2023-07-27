package com.odeyalo.sonata.artists.service.verification;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.InMemoryArtist;
import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.repository.AwaitingVerificationArtistStorage;
import com.odeyalo.sonata.artists.repository.InMemoryArtistRepository;
import com.odeyalo.sonata.suite.brokers.events.artist.registration.ArtistRegistrationVerificationRequiredEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Impl that does not do any verifications and set the completed to artist and save it to storage
 */
@Component
public class NoOpArtistRegistrationVerificationRequiredHandler implements ArtistRegistrationVerificationRequiredHandler {
    final AwaitingVerificationArtistStorage storage;
    final InMemoryArtistRepository repository;

    public NoOpArtistRegistrationVerificationRequiredHandler(AwaitingVerificationArtistStorage storage, InMemoryArtistRepository repository) {
        this.storage = storage;
        this.repository = repository;
    }

    @Override
    public Mono<Void> handleVerificationRequired(ArtistRegistrationVerificationRequiredEvent event) {
        return Mono.from((storage.findByUserId(event.getBody().getUserId()))
                        .map(artist -> {
                            artist.setStatus(new AwaitingVerificationArtist.CompletedStatus());
                            return artist;
                        }))
                .flatMap(artist -> storage.save(PersistableAwaitingVerificationArtist.from(artist)))
                .map(artist -> InMemoryArtist.builder().artistName(artist.getArtist().getArtistName()).userId(artist.getArtist().getUserId())
                        .id(1L)
                        .publicId(artist.getExternalId()).build())
                .flatMap(repository::save)
                .then();
    }
}
