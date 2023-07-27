package com.odeyalo.sonata.artists.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationVerificationTrackingDto {
    @JsonProperty("status")
    String status;
    ArtistMetadataDto metadataDto;

    public ArtistMetadataDto getMetadata() {
        return metadataDto;
    }
}
