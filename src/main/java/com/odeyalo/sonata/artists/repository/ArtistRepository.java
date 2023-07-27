package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.Artist;

public interface ArtistRepository<T extends Artist> extends ArtistPersistentOperations<T> {

    RepositoryType getRepositoryType();

}
