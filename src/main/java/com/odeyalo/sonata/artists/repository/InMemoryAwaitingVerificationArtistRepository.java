package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.InMemoryAwaitingVerificationArtist;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryAwaitingVerificationArtistRepository
        extends AbstractInMemoryBasicPersistentOperations<InMemoryAwaitingVerificationArtist, Long>
        implements AwaitingVerificationArtistRepository<InMemoryAwaitingVerificationArtist> {

    private final Map<String, Long> cacheByExternalId = new HashMap<>();
    private final Map<String, Long> cacheByUserId = new HashMap<>();

    @Override
    public Mono<InMemoryAwaitingVerificationArtist> save(InMemoryAwaitingVerificationArtist entity) {
        logger.info("Saved the user: {}", entity);
        return Mono.justOrEmpty(doSave(entity))
                .defaultIfEmpty(entity);
    }

    @Override
    public Mono<InMemoryAwaitingVerificationArtist> findByExternalId(String externalId) {
        return Mono.justOrEmpty(cacheByExternalId.get(externalId))
                .mapNotNull(cache::get);
    }

    @Override
    public Mono<InMemoryAwaitingVerificationArtist> findByUserId(String userId) {
        return Mono.justOrEmpty(cacheByUserId.get(userId))
                .mapNotNull(cache::get);
    }

    @Override
    public RepositoryType getRepositoryType() {
        return RepositoryType.IN_MEMORY;
    }

    private InMemoryAwaitingVerificationArtist doSave(InMemoryAwaitingVerificationArtist artist) {
        InMemoryAwaitingVerificationArtist saved = cache.put(artist.getInternalId(), artist);
        if (saved == null) {
            saved = artist;
        }
        createIndexes(saved);
        return saved;
    }

    private void createIndexes(InMemoryAwaitingVerificationArtist saved) {
        cacheByUserId.put(saved.getArtist().getUserId(), saved.getId());
        cacheByExternalId.put(saved.getExternalId(), saved.getId());
    }
}
