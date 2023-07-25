package com.odeyalo.sonata.artists.service.registration.standalone;

import com.odeyalo.sonata.artists.service.SocialMedia;
import com.odeyalo.sonata.artists.support.ReadOnlyCollection;
import com.odeyalo.suite.security.auth.AuthenticatedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class StandaloneArtistRegistrationPayload {
    // User that want to become an artist
    AuthenticatedUser user;
    String artistName;
    ReadOnlyCollection<String> genres;
    ReadOnlyCollection<SocialMedia> mediaContainer;
}
