package com.testing.faker;

import com.github.javafaker.Faker;
import com.testing.faker.media.InstagramAccount;
import com.testing.faker.media.TwitterAccount;

public class SocialMediaFaker {

    public static InstagramAccount randomInstagramAccount() {
        String username = Faker.instance().name().username();
        return randomInstagramAccount(username);
    }

    public static InstagramAccount randomInstagramAccount(String username) {
        return new InstagramAccount(username);
    }

    public static TwitterAccount randomTwitterAccount() {
        String username = Faker.instance().name().username();
        return randomTwitterAccount(username);
    }

    public static TwitterAccount randomTwitterAccount(String username) {
        return new TwitterAccount(username);
    }
}
