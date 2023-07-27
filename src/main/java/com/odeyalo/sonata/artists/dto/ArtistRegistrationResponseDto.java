package com.odeyalo.sonata.artists.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ArtistRegistrationResponseDto {
    private String type;
    @JsonProperty("tracking")
    private Tracking tracking;

    public static final String PENDING_TYPE = "pending";

    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class Tracking {
        @JsonProperty("url")
        String trackingUrl;
    }
}
