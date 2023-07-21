package com.testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.artists.dto.BecomeArtistDto;
import com.odeyalo.sonata.artists.dto.SocialMediaDto;
import com.testing.faker.media.InstagramAccount;
import com.testing.faker.media.TwitterAccount;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BecomeArtistDtoFaker {
    String artistName;
    Set<String> genres;
    Set<SocialMediaDto> socialMediaLinks;

    final Faker faker = Faker.instance();
    static final int MAX_GENRES = 10;

    protected BecomeArtistDtoFaker() {
        this.artistName = faker.artist().name();
        this.genres = randomGenres();
        this.socialMediaLinks = randomSocialMedias(artistName);
    }

    public static BecomeArtistDtoFaker create() {
        return new BecomeArtistDtoFaker();
    }

    public BecomeArtistDtoFaker overrideArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public BecomeArtistDtoFaker overrideGenres(Set<String> genres) {
        this.genres = genres;
        return this;
    }

    public BecomeArtistDtoFaker overrideSocialLinks(Set<SocialMediaDto> socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
        return this;
    }

    public BecomeArtistDto get() {
        return new BecomeArtistDto(artistName, genres, socialMediaLinks);
    }

    Set<SocialMediaDto> randomSocialMedias(String artistName) {
        artistName = artistName.replaceAll(" ", "_");
        InstagramAccount instagramAccount = SocialMediaFaker.randomInstagramAccount(artistName);
        TwitterAccount twitterAccount = SocialMediaFaker.randomTwitterAccount(artistName);
        SocialMediaDto instagram = SocialMediaDto.of(instagramAccount.getPlatform(), instagramAccount.getLink());
        SocialMediaDto twitter = SocialMediaDto.of(twitterAccount.getPlatform(), twitterAccount.getLink());
        return Set.of(instagram, twitter);
    }

    Set<String> randomGenres() {
        int numberOfGenres = faker.random().nextInt(1, MAX_GENRES);
        Set<String> res = new HashSet<>();
        for (int i = 0; i < numberOfGenres; i++) {
            res.add(faker.music().genre());
        }
        return res;
    }
}
