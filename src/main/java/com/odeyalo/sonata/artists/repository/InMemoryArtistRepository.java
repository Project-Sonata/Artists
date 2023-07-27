package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.InMemoryArtist;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class InMemoryArtistRepository extends AbstractInMemoryBasicPersistentOperations<InMemoryArtist, Long>
        implements ArtistRepository<InMemoryArtist> {


    @Override
    public Mono<InMemoryArtist> findArtistByPublicId(String publicId) {
        return null;
    }

    @Override
    public Mono<InMemoryArtist> findArtistByArtistName(String artistName) {
        return null;
    }

    @Override
    public Mono<InMemoryArtist> findArtistByUserId(String userId) {
        return null;
    }

    @Override
    public RepositoryType getRepositoryType() {
        return RepositoryType.IN_MEMORY;
    }

    @Override
    public Mono<InMemoryArtist> save(InMemoryArtist entity) {
        System.out.println("Save the entity: " + entity);
        return Mono.justOrEmpty(cache.put(entity.getId(), entity))
                .defaultIfEmpty(entity);
    }
}
