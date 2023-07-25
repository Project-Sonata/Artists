package com.odeyalo.sonata.artists.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.net.MalformedURLException;
import java.net.URL;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "empty")
public class InMemorySocialMediaLink implements SocialMediaLink {
    String platform;
    URL url;

    public static InMemorySocialMediaLink of(String platform, String url) {
        try {
            return of(platform, new URL(url));
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(String.format("The url: %s is malformed", url), ex);
        }
    }

    @Override
    public String getPlatformName() {
        return platform;
    }

    @Override
    public URL getLink() {
        return url;
    }
}
