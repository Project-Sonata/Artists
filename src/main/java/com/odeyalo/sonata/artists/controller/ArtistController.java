package com.odeyalo.sonata.artists.controller;

import com.odeyalo.sonata.artists.dto.ArtistRegistrationResponseDto.Tracking;
import com.odeyalo.sonata.artists.dto.BecomeArtistDto;
import com.odeyalo.sonata.artists.service.SocialMedia;
import com.odeyalo.sonata.artists.service.registration.standalone.StandaloneArtistRegistrationPayload;
import com.odeyalo.sonata.artists.service.registration.standalone.StandaloneArtistRegistrationService;
import com.odeyalo.sonata.artists.support.ReadOnlySet;
import com.odeyalo.suite.security.auth.AuthenticatedUser;
import com.odeyalo.suite.security.auth.AuthenticatedUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.odeyalo.sonata.artists.dto.ArtistRegistrationResponseDto.of;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    public static final String BECOME_ARTIST_MAPPING = "/become-artist";
    private final StandaloneArtistRegistrationService registrationService;

    @Autowired
    public ArtistController(StandaloneArtistRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = BECOME_ARTIST_MAPPING, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> becomeArtist(@RequestBody BecomeArtistDto body) {
        StandaloneArtistRegistrationPayload payload = createPayload(body);

        return registrationService.registerStandaloneArtist(payload)
                        .map(answer -> of(answer.getType(), Tracking.of("https://api.sonata.com/artist/tracking/status")))
                        .map(dto -> ResponseEntity.accepted()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(dto));
    }

    private static StandaloneArtistRegistrationPayload createPayload(BecomeArtistDto body) {
        Set<SocialMedia> medias = body.getSocialMedias().stream()
                .map(dto -> SocialMedia.of(dto.getPlatform(), dto.getLink()))
                .collect(Collectors.toSet());

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("read"),
                new SimpleGrantedAuthority("write")
        );

        AuthenticatedUser user = AuthenticatedUser.of(new AuthenticatedUserDetails("myID", "odeyalooo", "token", authorities),
                "token", new HashSet<>(authorities));

        return StandaloneArtistRegistrationPayload.builder()
                .user(user)
                .artistName(body.getArtistName())
                .mediaContainer(new ReadOnlySet<>(medias))
                .genres(new ReadOnlySet<>(body.getGenres()))
                .build();
    }
}
