package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;

public interface AwaitingVerificationArtistRepository<T extends AwaitingVerificationArtist>
        extends AwaitingVerificationArtistPersistentOperations<T> {

    RepositoryType getRepositoryType();

}
