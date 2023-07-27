package com.odeyalo.sonata.artists.controller;

import com.odeyalo.sonata.artists.dto.ArtistMetadataDto;
import com.odeyalo.sonata.artists.dto.RegistrationVerificationTrackingDto;
import com.odeyalo.sonata.artists.dto.SocialMediaDto;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist.CompletedStatus;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist.FailedStatus;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist.InProgress;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist.PendingStatus;
import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.repository.AwaitingVerificationArtistStorage;
import com.testing.faker.ArtistMetadataFaker;
import com.testing.faker.AwaitingVerificationArtistFaker;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.odeyalo.sonata.artists.controller.ArtistVerificationTrackingController.CURRENT_VERIFICATION_STATUS_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
@TestPropertySource(locations = "classpath:application-test.properties")
class ArtistVerificationTrackingControllerTest {

    @Autowired
    AwaitingVerificationArtistStorage storage;

    @Autowired
    WebTestClient webTestClient;

    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    public static final String PENDING_STATUS_ACCESS_TOKEN = "pending_status_access_token";
    public static final String COMPLETED_STATUS_ACCESS_TOKEN = "completed_status_access_token";
    public static final String IN_PROGRESS__STATUS_ACCESS_TOKEN = "in_progress_status_access_token";
    public static final String FAILED_STATUS_ACCESS_TOKEN = "failed_status_access_token";

    public static final String PENDING_USER_ID = "pending";
    public static final String COMPLETED_USER_ID = "completed";
    public static final String IN_PROGRESS_USER_ID = "in_progress";
    public static final String FAILED_USER_ID = "failed";

    @ParameterizedTest
    @ValueSource(strings = {PENDING_STATUS_ACCESS_TOKEN, COMPLETED_STATUS_ACCESS_TOKEN, IN_PROGRESS__STATUS_ACCESS_TOKEN, FAILED_STATUS_ACCESS_TOKEN})
    void requestCurrentStatus_andExpectOk(String token) {

        WebTestClient.ResponseSpec response = requestCurrentStatus(token);

        response.expectStatus().isOk();
    }

    @Test
    void requestCurrentStatus_andExpectValidStatus_Pending() {
        saveArtistWithUserIdAndStatus(PENDING_USER_ID, new PendingStatus());

        WebTestClient.ResponseSpec response = requestCurrentStatus(PENDING_STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        assertEquals("pending", body.getStatus());
    }

    @Test
    void requestCurrentStatus_andExpectValidStatus_Completed() {
        saveArtistWithUserIdAndStatus(COMPLETED_USER_ID, new CompletedStatus());

        WebTestClient.ResponseSpec response = requestCurrentStatus(COMPLETED_STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        assertEquals("completed", body.getStatus());
    }

    @Test
    void requestCurrentStatus_andExpectValidStatus_Failed() {
        saveArtistWithUserIdAndStatus(FAILED_USER_ID, new FailedStatus());

        WebTestClient.ResponseSpec response = requestCurrentStatus(FAILED_STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        assertEquals("failed", body.getStatus());
    }

    @Test
    void requestCurrentStatus_andExpectValidStatus_InProgress() {
        saveArtistWithUserIdAndStatus(IN_PROGRESS_USER_ID, new InProgress());

        WebTestClient.ResponseSpec response = requestCurrentStatus(IN_PROGRESS__STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        assertEquals("in_progress", body.getStatus());
    }

    @Test
    void requestCurrentStatusForInProgress_andExpectArtistMetadata() {
        PersistableAwaitingVerificationArtist savedArtist = saveArtistWithUserIdAndStatus(IN_PROGRESS_USER_ID, new InProgress());

        WebTestClient.ResponseSpec response = requestCurrentStatus(IN_PROGRESS__STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        ArtistMetadataDto expectedDto = createMetadata(savedArtist);

        assertEquals(expectedDto, body.getMetadata());
    }

    @Test
    void requestCurrentStatusForPending_andExpectArtistMetadata() {
        PersistableAwaitingVerificationArtist savedArtist = saveArtistWithUserIdAndStatus(PENDING_USER_ID, new PendingStatus());

        WebTestClient.ResponseSpec response = requestCurrentStatus(PENDING_STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        ArtistMetadataDto expectedDto = createMetadata(savedArtist);

        assertEquals(expectedDto, body.getMetadata());
    }

    @Test
    void requestCurrentStatusForFailed_andExpectArtistMetadata() {
        PersistableAwaitingVerificationArtist savedArtist = saveArtistWithUserIdAndStatus(FAILED_USER_ID, new FailedStatus());

        WebTestClient.ResponseSpec response = requestCurrentStatus(FAILED_STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        ArtistMetadataDto expectedDto = createMetadata(savedArtist);

        assertEquals(expectedDto, body.getMetadata());
    }

    @Test
    void requestCurrentStatusForCompleted_andExpectArtistMetadata() {
        PersistableAwaitingVerificationArtist savedArtist = saveArtistWithUserIdAndStatus(COMPLETED_USER_ID, new CompletedStatus());

        WebTestClient.ResponseSpec response = requestCurrentStatus(COMPLETED_STATUS_ACCESS_TOKEN);
        RegistrationVerificationTrackingDto body = response.expectBody(RegistrationVerificationTrackingDto.class).returnResult().getResponseBody();

        ArtistMetadataDto expectedDto = createMetadata(savedArtist);

        assertEquals(expectedDto, body.getMetadata());
    }



    // TODO: Rewrite it using converters
    private static ArtistMetadataDto createMetadata(PersistableAwaitingVerificationArtist artist) {
        AwaitingVerificationArtist.ArtistMetadata artistMetadata = artist.getArtistMetadata();
        return ArtistMetadataDto.of(artistMetadata.getUserId(), artistMetadata.getArtistName(), artistMetadata.getGenres(),
                artistMetadata.getSocialMedia().stream().map(media -> SocialMediaDto.of(media.getPlatformName(), media.getLink().toString())).toList());
    }

    @NotNull
    private WebTestClient.ResponseSpec requestCurrentStatus(String token) {
        return webTestClient.get().uri("/artist/tracking" + CURRENT_VERIFICATION_STATUS_ENDPOINT)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_PREFIX + token)
                .exchange();
    }

    private PersistableAwaitingVerificationArtist saveArtistWithUserIdAndStatus(String userId, AwaitingVerificationArtist.VerificationStatus requiredStatus) {
        AwaitingVerificationArtist artist = getAwaitingVerificationArtistWithUserIdAndStatus(userId, requiredStatus);
        return storage.save(PersistableAwaitingVerificationArtist.from(artist)).block();
    }

    private AwaitingVerificationArtist getAwaitingVerificationArtistWithUserIdAndStatus(String userId, AwaitingVerificationArtist.VerificationStatus status) {
        AwaitingVerificationArtist.ArtistMetadata artistMetadata = ArtistMetadataFaker.create().overrideUserId(userId).get();
        return AwaitingVerificationArtistFaker
                .create()
                .overrideArtist(artistMetadata)
                .overrideStatus(status)
                .get();
    }
}