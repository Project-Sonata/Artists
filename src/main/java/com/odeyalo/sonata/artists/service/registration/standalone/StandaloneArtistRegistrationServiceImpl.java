package com.odeyalo.sonata.artists.service.registration.standalone;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.InMemorySocialMediaLink;
import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.repository.AwaitingVerificationArtistStorage;
import com.odeyalo.sonata.artists.service.SocialMedia;
import com.odeyalo.sonata.artists.service.event.impl.ArtistRegistrationConfirmationRequiredEventPublisher;
import com.odeyalo.sonata.artists.support.ReadOnlyCollection;
import com.odeyalo.sonata.suite.brokers.events.artist.registration.ArtistRegistrationVerificationRequiredEvent;
import com.odeyalo.sonata.suite.brokers.events.artist.registration.data.AwaitingVerificationArtistInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Random;
import java.util.stream.StreamSupport;

import static com.odeyalo.sonata.artists.service.registration.standalone.StandaloneArtistRegistrationAnswer.PENDING;

/**
 * Default impl of StandaloneArtistRegistrationServiceImpl
 * The implementation do the following job:
 * - Prepare and save the {@link AwaitingVerificationArtist} entity to storage.
 * - Publish the {@link ArtistRegistrationVerificationRequiredEvent} using event publisher.
 * - Always return the StandaloneArtistRegistrationAnswer.of(PENDING) answer
 */
@Component
public class StandaloneArtistRegistrationServiceImpl implements StandaloneArtistRegistrationService {
    private final AwaitingVerificationArtistStorage artistStorage;
    private final ArtistRegistrationConfirmationRequiredEventPublisher eventPublisher;

    public StandaloneArtistRegistrationServiceImpl(AwaitingVerificationArtistStorage artistStorage, ArtistRegistrationConfirmationRequiredEventPublisher eventPublisher) {
        this.artistStorage = artistStorage;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Mono<StandaloneArtistRegistrationAnswer> registerStandaloneArtist(StandaloneArtistRegistrationPayload artistPayload) {
        List<InMemorySocialMediaLink> links = createInMemoryMediaLinks(artistPayload);

        PersistableAwaitingVerificationArtist awaitingVerificationArtist = createAwaitingVerificationArtistEntity(artistPayload, links);

        return artistStorage.save(awaitingVerificationArtist)
                .subscribeOn(Schedulers.parallel())
                .flatMap(savedArtist -> eventPublisher.publishEvent(createEventPayload(savedArtist)))
                .thenReturn(StandaloneArtistRegistrationAnswer.of(PENDING));
    }

    private PersistableAwaitingVerificationArtist createAwaitingVerificationArtistEntity(StandaloneArtistRegistrationPayload artistPayload, List<InMemorySocialMediaLink> links) {
        AwaitingVerificationArtist.ArtistMetadata artist = createArtistMetadata(artistPayload, links);

        return PersistableAwaitingVerificationArtist.builder()
                .id(new Random().nextLong(1000))
                .artist(artist)
                .externalId(RandomStringUtils.randomAlphanumeric(15))
                .status(new AwaitingVerificationArtist.PendingStatus())
                .build();
    }

    private AwaitingVerificationArtist.ArtistMetadata createArtistMetadata(StandaloneArtistRegistrationPayload artistPayload, List<InMemorySocialMediaLink> links) {
        return AwaitingVerificationArtist.ArtistMetadata.builder()
                .userId(artistPayload.getUser().getDetails().getId())
                .artistName(artistPayload.getArtistName())
                .socialMedia(links)
                .build();
    }

    private ArtistRegistrationVerificationRequiredEvent createEventPayload(PersistableAwaitingVerificationArtist savedArtist) {
        return new ArtistRegistrationVerificationRequiredEvent(
                AwaitingVerificationArtistInfo.of(savedArtist.getExternalId(),
                        savedArtist.getArtistMetadata().getUserId(),
                        savedArtist.getArtistMetadata().getArtistName(),
                        savedArtist.getArtist().getGenres())
        );
    }

    private List<InMemorySocialMediaLink> createInMemoryMediaLinks(StandaloneArtistRegistrationPayload artistPayload) {
        ReadOnlyCollection<SocialMedia> mediaContainer = artistPayload.getMediaContainer();
        return StreamSupport.stream(mediaContainer.spliterator(), false)
                .map(val -> InMemorySocialMediaLink.of(val.getPlatform(), val.getLink()))
                .toList();
    }
}
