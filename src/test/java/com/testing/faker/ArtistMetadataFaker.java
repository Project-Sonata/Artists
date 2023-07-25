package com.testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.artists.entity.AwaitingVerificationArtist;
import com.odeyalo.sonata.artists.entity.InMemorySocialMediaLink;
import com.odeyalo.sonata.artists.entity.SocialMediaLink;
import com.testing.faker.media.InstagramAccount;
import com.testing.faker.media.TwitterAccount;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArtistMetadataFaker {
    String userId;
    String artistName;
    List<String> genres;
    List<? extends SocialMediaLink> socialMedia;

    final Faker faker = Faker.instance();
    static final int MAX_GENRES = 10;

    protected ArtistMetadataFaker() {
        this.userId = faker.internet().uuid();
        this.artistName = faker.artist().name();
        this.genres = randomGenres();
        this.socialMedia = randomSocialMedias(artistName);
    }

    public static ArtistMetadataFaker create() {
        return new ArtistMetadataFaker();
    }

    public ArtistMetadataFaker overrideUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public ArtistMetadataFaker overrideArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public ArtistMetadataFaker overrideGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public ArtistMetadataFaker overrideSocialMedia(List<? extends SocialMediaLink> socialMedia) {
        this.socialMedia = socialMedia;
        return this;
    }

    public AwaitingVerificationArtist.ArtistMetadata get() {
        return AwaitingVerificationArtist.ArtistMetadata.builder()
                .userId(userId)
                .artistName(artistName)
                .genres(genres)
                .socialMedia(socialMedia)
                .build();
    }

    List<InMemorySocialMediaLink> randomSocialMedias(String artistName) {
        artistName = artistName.replaceAll(" ", "_");
        InstagramAccount instagramAccount = SocialMediaFaker.randomInstagramAccount(artistName);
        TwitterAccount twitterAccount = SocialMediaFaker.randomTwitterAccount(artistName);
        return List.of(instagramAccount, twitterAccount)
                .stream().map(account -> InMemorySocialMediaLink.of(account.getPlatform(), account.getLink()))
                .toList();
    }

    List<String> randomGenres() {

        int numberOfGenres = faker.random().nextInt(1, MAX_GENRES);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < numberOfGenres; i++) {
            res.add(faker.music().genre());
        }
        return res;
    }
}
