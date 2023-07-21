package com.odeyalo.sonata.artists.repository;

import reactor.core.publisher.Mono;

/**
 * Basic operations for any entity
 * @param <T> - entity
 * @param <ID> - id of the entity
 */
public interface BasicPersistentOperations<T, ID> {

    Mono<T> findById(ID id);

    Mono<T> save(T entity);

    Mono<Void> deleteById(ID id);

}
