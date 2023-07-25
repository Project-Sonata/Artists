package com.odeyalo.sonata.artists.service.registration;

import com.odeyalo.sonata.artists.service.registration.standalone.StandaloneArtistRegistrationService;

/**
 * Marker interface that indicates process of the registration
 * The interface has requirements:
 * - Artist must be verified
 * - Artist must be not saved directly before verification completes
 * @see StandaloneArtistRegistrationService
 */
public interface ArtistRegistrationService {

}