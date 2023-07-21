package com.odeyalo.sonata.artists.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    public static final String BECOME_ARTIST_MAPPING = "/become_artist";

    @PostMapping(value = BECOME_ARTIST_MAPPING, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<?> becomeArtist() {
        return null;
    }
}
