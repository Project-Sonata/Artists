package com.odeyalo.sonata.artists.controller;

import com.odeyalo.sonata.artists.dto.ArtistRegistrationResponseDto;
import com.odeyalo.sonata.artists.dto.BecomeArtistDto;
import com.testing.SharedComponent;
import com.testing.faker.BecomeArtistDtoFaker;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Hooks;

import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode.REMOTE;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@AutoConfigureStubRunner(stubsMode = REMOTE,
        repositoryRoot = "git://https://github.com/Project-Sonata/Sonata-Contracts.git",
        ids = "com.odeyalo.sonata:authorization:+")
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(SharedComponent.class)
class ArtistControllerTest {

    @Autowired
    WebTestClient webTestClient;

    static final String AUTHORIZATION_PREFIX = "Bearer ";
    static final String VALID_ACCESS_TOKEN = "mikunakanoisthebestgirl";
    static final String INVALID_ACCESS_TOKEN = "invalidtoken";
    public static final String MAPPING_PREFIX = "/artist";

    @BeforeAll
    public void setup() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.of(1, ChronoUnit.MINUTES))
                .build();
        Hooks.onOperatorDebug();
    }

    @Nested
    @Import(SharedComponent.class)
    class BecomeArtistWithValidRequest {
        @Test
        void expectApplicationJson() {
            WebTestClient.ResponseSpec responseSpec = prepareAndValidBecomeArtistRequest();
            responseSpec.expectHeader().contentType(MediaType.APPLICATION_JSON);
        }

        @Test
        void expectStatus202() {
            WebTestClient.ResponseSpec responseSpec = prepareAndValidBecomeArtistRequest();
            responseSpec.expectStatus().isAccepted();
        }

        @Test
        void expectNotNullResponseBody() {
            WebTestClient.ResponseSpec responseSpec = prepareAndValidBecomeArtistRequest();
            ArtistRegistrationResponseDto body = responseSpec.expectBody(ArtistRegistrationResponseDto.class).returnResult().getResponseBody();
            assertNotNull(body);
        }

        @Test
        void expectPendingType() {
            WebTestClient.ResponseSpec responseSpec = prepareAndValidBecomeArtistRequest();
            ArtistRegistrationResponseDto body = responseSpec.expectBody(ArtistRegistrationResponseDto.class).returnResult().getResponseBody();
            assertEquals(ArtistRegistrationResponseDto.PENDING_TYPE, body.getType());
        }

        @Test
        void expectTrackingUrlToBeNotNull() {
            WebTestClient.ResponseSpec responseSpec = prepareAndValidBecomeArtistRequest();
            ArtistRegistrationResponseDto body = responseSpec.expectBody(ArtistRegistrationResponseDto.class).returnResult().getResponseBody();
            assertNotNull(body.getTracking());
        }

        @Test
        void expectTrackingUrlToBeValidURL() {
            WebTestClient.ResponseSpec responseSpec = prepareAndValidBecomeArtistRequest();
            ArtistRegistrationResponseDto body = responseSpec.expectBody(ArtistRegistrationResponseDto.class).returnResult().getResponseBody();
            assertDoesNotThrow(() -> new URL(body.getTracking().getTrackingUrl()), "The url must be valid!");
        }

        private WebTestClient.ResponseSpec prepareAndValidBecomeArtistRequest() {
            BecomeArtistDto dto = BecomeArtistDtoFaker.create().get();
            return sendRequest(dto, VALID_ACCESS_TOKEN);
        }
    }

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
            return sendRequest(body, INVALID_ACCESS_TOKEN);
        }
    }


    @NotNull
    private WebTestClient.ResponseSpec sendRequest(BecomeArtistDto dto, String token) {
        return webTestClient.post()
                .uri(MAPPING_PREFIX + ArtistController.BECOME_ARTIST_MAPPING)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_PREFIX + token)
                .bodyValue(dto)
                .exchange();
    }
}