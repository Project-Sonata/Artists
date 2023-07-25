package com.odeyalo.sonata.artists.service.registration.standalone;

import com.odeyalo.sonata.artists.entity.InMemoryAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.repository.AwaitingVerificationArtistStorage;
import com.odeyalo.sonata.artists.repository.InMemoryAwaitingVerificationArtistRepository;
import com.odeyalo.sonata.artists.repository.SimpleDelegateAwaitingVerificationArtistStorage;
import com.odeyalo.sonata.artists.service.event.DefaultEventSource;
import com.odeyalo.sonata.artists.service.event.EmittingEventSource;
import com.odeyalo.sonata.artists.service.event.InternalEventPublisherDelegate;
import com.odeyalo.sonata.artists.service.event.impl.ArtistRegistrationConfirmationRequiredEventPublisher;
import com.odeyalo.sonata.artists.support.ReadOnlySet;
import com.odeyalo.suite.security.auth.AuthenticatedUser;
import com.odeyalo.suite.security.auth.AuthenticatedUserDetails;
import com.testing.TestingEventListenerSpy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StandaloneArtistRegistrationServiceImplTest {
    EmittingEventSource eventSource = new DefaultEventSource();

    ArtistRegistrationConfirmationRequiredEventPublisher publisher = new ArtistRegistrationConfirmationRequiredEventPublisher(
            new InternalEventPublisherDelegate(eventSource)
    );

    InMemoryAwaitingVerificationArtistRepository repository = new InMemoryAwaitingVerificationArtistRepository();

    AwaitingVerificationArtistStorage artistStorage = new SimpleDelegateAwaitingVerificationArtistStorage(List.of(repository));

    StandaloneArtistRegistrationServiceImpl standaloneArtistRegistrationService =
            new StandaloneArtistRegistrationServiceImpl(artistStorage, publisher);

    @Test
    void shouldPublishEvent() {
        TestingEventListenerSpy spy = new TestingEventListenerSpy(eventSource);

        StandaloneArtistRegistrationPayload payload = createPayload();

        standaloneArtistRegistrationService.registerStandaloneArtist(payload).block();

        assertTrue(spy.wasInvoked(), "Events must be invoked!");
    }

    @Test
    void shouldSaveEntity() {
        StandaloneArtistRegistrationPayload payload = createPayload();

        standaloneArtistRegistrationService.registerStandaloneArtist(payload).block();

        PersistableAwaitingVerificationArtist entity = artistStorage.findByUserId(payload.getUser().getDetails().getId()).block();

        assertNotNull(entity, "The entity must be saved!");
    }

    @Test
    void shouldPendingStatusToBeReturned() {
        StandaloneArtistRegistrationPayload payload = createPayload();

        StandaloneArtistRegistrationAnswer answer = standaloneArtistRegistrationService.registerStandaloneArtist(payload).block();

        assertEquals(StandaloneArtistRegistrationAnswer.PENDING, answer.getType());
    }

    private StandaloneArtistRegistrationPayload createPayload() {
        return StandaloneArtistRegistrationPayload.of(
                new AuthenticatedUser(
                        new AuthenticatedUserDetails("myID", "odeyalooo", "password", List.of(new SimpleGrantedAuthority("read"))),
                        "creds", Set.of(new SimpleGrantedAuthority("read"))), "odeyalobeats", new ReadOnlySet<>(), new ReadOnlySet<>());
    }
}

