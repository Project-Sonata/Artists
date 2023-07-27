package com.odeyalo.sonata.artists.service.tracking;

import com.odeyalo.sonata.artists.repository.AwaitingVerificationArtistStorage;
import com.odeyalo.sonata.artists.service.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArtistRegistrationVerificationTrackingServiceImpl implements ArtistRegistrationVerificationTrackingService {
    private final AwaitingVerificationArtistStorage artistStorage;
    private final Logger logger = LoggerFactory.getLogger(ArtistRegistrationVerificationTrackingServiceImpl.class);

    @Autowired
    public ArtistRegistrationVerificationTrackingServiceImpl(AwaitingVerificationArtistStorage artistStorage) {
        this.artistStorage = artistStorage;
    }

    @Override
    public Mono<RegistrationTrackingData> getRegistrationTrackingData(UserInfo userInfo) {
        logger.info("Retrieve info for: {}", userInfo);
        return artistStorage.findByUserId(userInfo.getId())
                .map(artist -> RegistrationTrackingData.of(artist.getStatus().asEnum(), artist.getArtistMetadata()));
    }
}
