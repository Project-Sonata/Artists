package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.InMemoryAwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SimpleDelegateAwaitingVerificationArtistStorage implements AwaitingVerificationArtistStorage {
    final AwaitingVerificationArtistRepository<AwaitingVerificationArtist> repository;

    @Autowired
    public SimpleDelegateAwaitingVerificationArtistStorage(List<AwaitingVerificationArtistRepository<? extends AwaitingVerificationArtist>> repositories) {
        if (repositories.isEmpty()) {
            throw new IllegalStateException("At least 1 repository must present!");
        }
        this.repository = (AwaitingVerificationArtistRepository<AwaitingVerificationArtist>) repositories.get(0);
    }

    @Override
    public Mono<PersistableAwaitingVerificationArtist> findByExternalId(String externalId) {
        return repository.findByExternalId(externalId)
                .map(PersistableAwaitingVerificationArtist::from);
    }

    @Override
    public Mono<PersistableAwaitingVerificationArtist> findByUserId(String userId) {
        return repository.findByUserId(userId)
                .map(PersistableAwaitingVerificationArtist::from);    }

    @Override
    public Mono<PersistableAwaitingVerificationArtist> findById(Long id) {
        return repository.findById(id)
            .map(PersistableAwaitingVerificationArtist::from);
    }

    @Override
    public Mono<PersistableAwaitingVerificationArtist> save(PersistableAwaitingVerificationArtist entity) {
        return repository.save(convertToSpecificEntity(entity))
                .map(PersistableAwaitingVerificationArtist::from);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    private AwaitingVerificationArtist convertToSpecificEntity(PersistableAwaitingVerificationArtist entity) {
        if (repository.getRepositoryType() == RepositoryType.IN_MEMORY) {
            return InMemoryAwaitingVerificationArtist.from(entity);
        }

        return null;
    }
}
