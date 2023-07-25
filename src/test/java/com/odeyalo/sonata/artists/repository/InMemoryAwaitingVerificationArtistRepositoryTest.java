package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.InMemoryAwaitingVerificationArtist;
import com.odeyalo.sonata.suite.brokers.events.artist.registration.data.AwaitingVerificationArtistInfo;
import com.testing.faker.ArtistMetadataFaker;
import com.testing.faker.AwaitingVerificationArtistFaker;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAwaitingVerificationArtistRepositoryTest {
    InMemoryAwaitingVerificationArtistRepository repo = new InMemoryAwaitingVerificationArtistRepository();

    @Test
    void save_andExpectToBeSaved() {
        // given
        InMemoryAwaitingVerificationArtist artist = AwaitingVerificationArtistFaker.create().inMemory();
        // when
        InMemoryAwaitingVerificationArtist saved = repo.save(artist).block();
        // then
        InMemoryAwaitingVerificationArtist found = repo.findById(saved.getId()).block();

        assertNotNull(found, "The artist must be saved!");
        assertEquals(saved, found, "Artist must be equal");
    }

    @Test
    void findByExternalId() {
        // given
        InMemoryAwaitingVerificationArtist artist = AwaitingVerificationArtistFaker.create().inMemory();
        // when
        InMemoryAwaitingVerificationArtist saved = repo.save(artist).block();
        // then
        InMemoryAwaitingVerificationArtist found = repo.findByExternalId(saved.getExternalId()).block();

        assertEquals(saved, found, "Artist must be found");
    }

    @Test
    void findByNotExistingExternalId_andExpectNull() {
        // given
        InMemoryAwaitingVerificationArtist artist = AwaitingVerificationArtistFaker.create().inMemory();
        // when
        InMemoryAwaitingVerificationArtist saved = repo.save(artist).block();
        // then
        InMemoryAwaitingVerificationArtist found = repo.findByExternalId("not exist").block();

        assertNull(found, "If external id not exist null must be returned");
    }

    @Test
    void findByUserId() {
        // given
        InMemoryAwaitingVerificationArtist artist = AwaitingVerificationArtistFaker.create().inMemory();
        // when
        InMemoryAwaitingVerificationArtist saved = repo.save(artist).block();
        // then
        InMemoryAwaitingVerificationArtist found = repo.findByUserId(saved.getArtist().getUserId()).block();

        assertEquals(saved, found, "Artist must be found");
    }

    @Test
    void findByNotExistingUserId_andExpectNull() {
        // given
        InMemoryAwaitingVerificationArtist artist = AwaitingVerificationArtistFaker.create().inMemory();
        // when
        InMemoryAwaitingVerificationArtist saved = repo.save(artist).block();
        // then
        InMemoryAwaitingVerificationArtist found = repo.findByUserId("not exist").block();

        assertNull(found, "If value not exist - null must be returned");
    }


    @Test
    void getRepositoryType() {
        assertEquals(RepositoryType.IN_MEMORY, repo.getRepositoryType());
    }
}