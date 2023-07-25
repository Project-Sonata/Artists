package com.odeyalo.sonata.artists.repository;

import com.odeyalo.sonata.artists.entity.PersistableAwaitingVerificationArtist;

/**
 * Bridge between repository and service, can be used as delegate or do some job, like load balancing, checking the health of repository and save on condition
 */
public interface AwaitingVerificationArtistStorage  extends AwaitingVerificationArtistPersistentOperations<PersistableAwaitingVerificationArtist> {
}
