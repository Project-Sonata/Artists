package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import reactor.core.publisher.Mono;

/**
 * Basic persistent operations for AwaitingVerificationArtist
 * @param <T>
 */
public interface AwaitingVerificationArtistPersistentOperations<T extends AwaitingVerificationArtist> extends BasicPersistentOperations<T, Long> {

    Mono<T> findByExternalId(String externalId);

    Mono<T> findByUserId(String userId);
}
