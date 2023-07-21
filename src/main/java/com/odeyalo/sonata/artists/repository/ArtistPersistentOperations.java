package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.Artist;
import reactor.core.publisher.Mono;

/**
 * Operations that can be done only for artist entity
 * @param <T> - artist
 */
public interface ArtistPersistentOperations<T extends Artist> extends BasicPersistentOperations<T, String> {

    Mono<T> findArtistByPublicId(String publicId);

    Mono<T> findArtistByArtistName(String artistName);

    Mono<T> findArtistByUserId(String userId);
}
