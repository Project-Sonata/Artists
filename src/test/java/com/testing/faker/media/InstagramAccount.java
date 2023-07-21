package com.testing.faker.media;

public class InstagramAccount implements SocialMediaAccount {
    private final String username;

    public InstagramAccount(String username) {
        this.username = username;
    }
    @Override
    public String getPlatform() {
        return "Instagram";
    }

    @Override
    public String getLink() {
        return String.format("https://www.instagram.com/%s/", username);
    }
}
