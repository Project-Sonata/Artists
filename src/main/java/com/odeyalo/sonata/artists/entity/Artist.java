package com.odeyalo.sonata.artists.entity;

import java.util.List;

/**
 * Represent the Artist entity in Sonata Project.
 */
public interface Artist {
    /**
     * Unique artist id represented in long. Used to reduce amount of memory for indexes.
     * For real cases {@link #getArtistPublicId()} should be used.
     * For internal use only
     * @return - ID of the artist represented in long
     */
    Long getId();

    /**
     * Public id of the used.
     * Instead of long, id should be like this: il0veM1KuNakAn0
     * Only this url must be exposed to API
     * @return - ID of the artist
     */
    String getArtistPublicId();

    /**
     * User associated with the artist page
     * The user ID can be null when music distributor created this artist
     * @return - user id associated with artist
     */
    String getUserId();

    String getArtistName();

    String getProfileImageUrl();

    String getBackgroundImageUrl();

    String getAboutMe();

    long getCreationTime();

    long getMonthlyListeners();

    long getFollowersCount();

    List<SocialMediaLink> getSocialMedias();

    List<String> getGenres();
}
