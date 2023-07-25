package com.odeyalo.sonata.artists.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Generic AwaitingVerificationArtist that does not depend on specific entity
 * and used as bridge
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersistableAwaitingVerificationArtist implements AwaitingVerificationArtist {
    Long id;
    String externalId;
    ArtistMetadata artist;
    VerificationStatus status;

    public static PersistableAwaitingVerificationArtist from(AwaitingVerificationArtist artist) {
        return PersistableAwaitingVerificationArtist.builder()
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