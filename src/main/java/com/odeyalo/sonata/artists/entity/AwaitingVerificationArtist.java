package com.odeyalo.sonata.artists.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Represent the entity of artist that waits for the verification
 */
public interface AwaitingVerificationArtist {
    /**
     * Id of the artist that used only in this service. It can be changed
     * @return - id for internal system
     */
    Long getInternalId();

    /**
     * Id of the artist that can be used to share the data between service. It is constant all the time.
     * Used to access from WEB endpoints, to share using events(using kafka, for example) between microservices
     * @return - public if for the entity
     */
    String getExternalId();


    ArtistMetadata getArtistMetadata();

    VerificationStatus getCurrentVerificationStatus();

    void updateVerificationStatus(VerificationStatus status);

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder
    class ArtistMetadata {
        String userId;
        String artistName;
        List<String> genres;
        List<? extends SocialMediaLink> socialMedia;
    }

    interface VerificationStatus {
        // When this status was triggered
        long getStatusTime();

        RegistrationVerificationStatus asEnum();
    }
    class PendingStatus implements VerificationStatus {

        @Override
        public long getStatusTime() {
            return System.currentTimeMillis();
        }

        @Override
        public RegistrationVerificationStatus asEnum() {
            return RegistrationVerificationStatus.PENDING;
        }
    }

    class InProgress implements VerificationStatus {

        @Override
        public long getStatusTime() {
            return System.currentTimeMillis();
        }

        @Override
        public RegistrationVerificationStatus asEnum() {
            return RegistrationVerificationStatus.IN_PROGRESS;
        }
    }

    class CompletedStatus implements VerificationStatus {
        String completionDate;
        String approvalMessage;

        @Override
        public long getStatusTime() {
            return System.currentTimeMillis();
        }

        @Override
        public RegistrationVerificationStatus asEnum() {
            return RegistrationVerificationStatus.COMPLETED;
        }
    }

    class FailedStatus implements VerificationStatus {
        String reason;
        @Override
        public long getStatusTime() {
            return System.currentTimeMillis();
        }

        @Override
        public RegistrationVerificationStatus asEnum() {
            return RegistrationVerificationStatus.FAILED;
        }
    }
}
