package com.odeyalo.sonata.artists.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for In-Memory databases
 * @param <T> entity
 * @param <ID> - id of the entity
 */
public abstract class AbstractInMemoryBasicPersistentOperations<T, ID>
        implements BasicPersistentOperations<T, ID> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Map<ID, T> cache = new HashMap<>();

    @Override
    public Mono<T> findById(ID id) {
        return Mono.just(cache.get(id));
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return Mono.just(cache.remove(id)).then();
    }
}
