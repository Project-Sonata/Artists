package com.testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist.VerificationStatus;
import com.odeyalo.sonata.artists.entity.InMemoryAwaitingVerificationArtist;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AwaitingVerificationArtistFaker {
    Long id;
    String externalId;
    AwaitingVerificationArtist.ArtistMetadata artist;
    VerificationStatus status;

    final Faker faker = Faker.instance();

    protected AwaitingVerificationArtistFaker() {
        this.id = faker.number().randomNumber();
        this.externalId = faker.internet().uuid();
        this.artist = ArtistMetadataFaker.create().get();
        this.status = new AwaitingVerificationArtist.PendingStatus();
    }


    public static AwaitingVerificationArtistFaker create() {
        return new AwaitingVerificationArtistFaker();
    }

    public AwaitingVerificationArtistFaker overrideId(Long id) {
        this.id = id;
        return this;
    }

    public AwaitingVerificationArtistFaker overrideExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public AwaitingVerificationArtistFaker overrideArtist(AwaitingVerificationArtist.ArtistMetadata artist) {
        this.artist = artist;
        return this;
    }

    public AwaitingVerificationArtistFaker overrideStatus(VerificationStatus status) {
        this.status = status;
        return this;
    }

    public InMemoryAwaitingVerificationArtist inMemory() {
        return InMemoryAwaitingVerificationArtist.builder()
                .id(id)
                .externalId(externalId)
                .artist(artist)
                .status(status)
                .build();
    }

    public AwaitingVerificationArtist get() {
        return InMemoryAwaitingVerificationArtist.builder()
                .id(id)
                .externalId(externalId)
                .artist(artist)
                .status(status)
                .build();
    }
}
