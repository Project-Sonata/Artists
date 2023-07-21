package com.odeyalo.sonata.artists.entity;

import java.net.URL;

/**
 * Entity to represent social media of the artist.
 */
public interface SocialMediaLink {
    /**
     * Name of the platform. Examples: "Instagram", "Twitter", "Telegram", etc
     * @return name of the platform.
     */
    String getPlatformName();

    /**
     * Url to the social media.
     * @return valid url
     */
    URL getLink();

}
