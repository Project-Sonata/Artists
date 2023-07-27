package com.odeyalo.sonata.artists.controller;

import com.odeyalo.sonata.artists.dto.ArtistMetadataDto;
import com.odeyalo.sonata.artists.dto.RegistrationVerificationTrackingDto;
import com.odeyalo.sonata.artists.dto.SocialMediaDto;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.service.UserInfo;
import com.odeyalo.sonata.artists.service.tracking.ArtistRegistrationVerificationTrackingService;
import com.odeyalo.suite.security.auth.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/artist/tracking")
public class ArtistVerificationTrackingController {

    private final ArtistRegistrationVerificationTrackingService trackingService;

    public static final String CURRENT_VERIFICATION_STATUS_ENDPOINT = "/status";

    @Autowired
    public ArtistVerificationTrackingController(ArtistRegistrationVerificationTrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping(value = CURRENT_VERIFICATION_STATUS_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RegistrationVerificationTrackingDto> currentStatus() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    AuthenticatedUser user = (AuthenticatedUser) authentication;
                    return UserInfo.of(user.getDetails().getId(), user.getName());
                })
                .flatMap(trackingService::getRegistrationTrackingData)
                .map(trackingData -> RegistrationVerificationTrackingDto.of(trackingData.getStatus().getName(), createMetadata(trackingData.getArtistMetadata())));
    }

    private static ArtistMetadataDto createMetadata(AwaitingVerificationArtist.ArtistMetadata artistMetadata) {
        return ArtistMetadataDto.of(artistMetadata.getUserId(), artistMetadata.getArtistName(), artistMetadata.getGenres(),
                artistMetadata.getSocialMedia().stream().map(media -> SocialMediaDto.of(media.getPlatformName(), media.getLink().toString())).toList());
    }
}
