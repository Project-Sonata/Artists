package com.odeyalo.sonata.artists.service.registration.standalone;

import com.odeyalo.sonata.artists.exception.InvalidRegistrationPayloadException;
import com.odeyalo.sonata.artists.exception.RegistrationException;
import reactor.core.publisher.Mono;

/**
 * The StandaloneArtistRegistrationService interface defines the contract for registering
 * artist accounts initiated by standalone users.
 * <p>
 * This service handles the specific logic and operations required to register artist accounts
 * for users who create artists directly from the application.
 * <p>
 * Implementations of this interface will provide methods for validating artist data,
 * performing necessary verifications, and saving the artist's information to the database.
 * Implementations should do this job async and return {@link StandaloneArtistRegistrationAnswer#PENDING}
 * status instead of doing all the job in the same request
 * <p>
 * Clients can use the methods defined in this interface to initiate the artist registration
 * process and obtain {@link StandaloneArtistRegistrationAnswer}.
 */
public interface StandaloneArtistRegistrationService {

    /**
     * Registers a standalone artist account for a user-initiated artist creation.
     * <p>
     * This method handles the registration process for artist accounts created directly
     * by standalone users within the application.
     * Implementations should do the job async, returning the {@link StandaloneArtistRegistrationAnswer#PENDING} status
     * @param artistPayload The payload containing the required data for artist registration.
     * @return A Mono emitting the ArtistRegistrationAnswer indicating the result of the registration process.
     * @throws InvalidRegistrationPayloadException If the provided artistPayload is invalid or contains missing data.
     * @throws RegistrationException    If an error occurs during the registration process.
     * @since 1.0
     */
    Mono<StandaloneArtistRegistrationAnswer> registerStandaloneArtist(StandaloneArtistRegistrationPayload artistPayload);
}
