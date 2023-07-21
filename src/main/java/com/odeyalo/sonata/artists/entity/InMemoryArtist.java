package com.odeyalo.sonata.artists.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InMemoryArtist implements Artist {
    long id;
    String publicId;
    String userId;
    String artistName;
    String profileImage;
    String backgroundImage;
    String aboutMe;
    long creationTime;
    long monthlyListeners;
    long followersCount;
    List<SocialMediaLink> socialMediaLinks;
    List<String> genres;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getArtistPublicId() {
        return publicId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getArtistName() {
        return artistName;
    }

    @Override
    public String getProfileImageUrl() {
        return profileImage;
    }

    @Override
    public String getBackgroundImageUrl() {
        return backgroundImage;
    }

    @Override
    public String getAboutMe() {
        return aboutMe;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public long getMonthlyListeners() {
        return monthlyListeners;
    }

    @Override
    public long getFollowersCount() {
        return followersCount;
    }

    @Override
    public List<SocialMediaLink> getSocialMedias() {
        return new ArrayList<>(socialMediaLinks);
    }

    @Override
    public List<String> getGenres() {
        return genres;
    }
}
