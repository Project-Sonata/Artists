package com.odeyalo.sonata.artists.service.registration.standalone;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class StandaloneArtistRegistrationAnswer {
    String type;

    public static final String PENDING = "pending";
    public static final String FAILED = "failed";
    public static final String COMPLETED = "completed";
}
