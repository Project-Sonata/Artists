package com.odeyalo.sonata.artists.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BecomeArtistDto {
    @JsonProperty("artist_name")
    private String artistName;
    @JsonProperty("genres")
    private Set<String> genres;
    @JsonProperty("social_medias")
    private Set<SocialMediaDto> socialMedias;
}