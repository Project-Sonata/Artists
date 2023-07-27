package com.odeyalo.sonata.artists.service.tracking;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.RegistrationVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class RegistrationTrackingData {
    RegistrationVerificationStatus status;
    AwaitingVerificationArtist.ArtistMetadata artistMetadata;
}
