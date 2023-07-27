package com.odeyalo.sonata.artists.service.tracking;

import com.odeyalo.sonata.artists.service.UserInfo;
import reactor.core.publisher.Mono;

/**
 * By requirements of Sonata, artist should be verificated if and only if the used requested artist creation.
 */
public interface ArtistRegistrationVerificationTrackingService {

    Mono<RegistrationTrackingData> getRegistrationTrackingData(UserInfo userInfo);

}
