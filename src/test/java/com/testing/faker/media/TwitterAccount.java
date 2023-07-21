package com.testing.faker.media;

/**
 * Represent the account in twitter
 */
public class TwitterAccount implements SocialMediaAccount {
    private final String username;

    public TwitterAccount(String username) {
        this.username = username;
    }

    @Override
    public String getPlatform() {
        return "Twitter";
    }

    @Override
    public String getLink() {
        return "https://twitter.com/" + username;
    }
}
