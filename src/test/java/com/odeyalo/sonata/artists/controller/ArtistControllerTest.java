package com.odeyalo.sonata.artists.controller;

import com.odeyalo.sonata.artists.dto.BecomeArtistDto;
import com.testing.SharedComponent;
import com.testing.faker.BecomeArtistDtoFaker;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
//@AutoConfigureStubRunner(stubsMode = REMOTE,
//        repositoryRoot = "git://https://github.com/Project-Sonata/Sonata-Contracts.git",
//        ids = "com.odeyalo.sonata:authorization:+")
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(SharedComponent.class)
class ArtistControllerTest {

    @Autowired
    WebTestClient webTestClient;

    static final String VALID_ACCESS_TOKEN = "mikunakanoisthebestgirl";
    static final String INVALID_ACCESS_TOKEN = "invalidtoken";
    public static final String MAPPING_PREFIX = "/artist";
    @Nested
    @Import(SharedComponent.class)
    class BecomeArtistWithUnauthorizedRequest {
        @Test
        void expectApplicationJson() {
            WebTestClient.ResponseSpec responseSpec = prepareUnauthorizedRequestAndSend();
            responseSpec.expectHeader().contentType(MediaType.APPLICATION_JSON);
        }

        @Test
        void expectStatus401() {
            WebTestClient.ResponseSpec responseSpec = prepareUnauthorizedRequestAndSend();
            responseSpec.expectStatus().isUnauthorized();
        }

        WebTestClient.ResponseSpec prepareUnauthorizedRequestAndSend() {
            BecomeArtistDto body = BecomeArtistDtoFaker.create().get();
            return webTestClient.post()
                    .uri(MAPPING_PREFIX + ArtistController.BECOME_ARTIST_MAPPING)
                    .header(HttpHeaders.AUTHORIZATION, INVALID_ACCESS_TOKEN)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
                    .exchange();
        }
    }
}