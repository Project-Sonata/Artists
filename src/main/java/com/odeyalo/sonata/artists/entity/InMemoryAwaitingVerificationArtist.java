package com.odeyalo.sonata.artists.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InMemoryAwaitingVerificationArtist implements AwaitingVerificationArtist {
    Long id;
    String externalId;
    ArtistMetadata artist;
    VerificationStatus status;

    public static InMemoryAwaitingVerificationArtist from(AwaitingVerificationArtist artist) {
        return InMemoryAwaitingVerificationArtist.builder()
                .id(artist.getInternalId())
                .externalId(artist.getExternalId())
                .artist(artist.getArtistMetadata())
                .status(artist.getCurrentVerificationStatus())
                .build();
    }


    @Override
    public Long getInternalId() {
        return id;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public ArtistMetadata getArtistMetadata() {
        return artist;
    }

    @Override
    public VerificationStatus getCurrentVerificationStatus() {
        return status;
    }

    @Override
    public void updateVerificationStatus(VerificationStatus status) {
        this.status = status;
    }
}
