package com.odeyalo.sonata.artists.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class ArtistMetadataDto {
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("artist_name")
    String artistName;
    @JsonProperty("genres")
    List<String> genres;
    @JsonProperty("social_medias")
    List<SocialMediaDto> socialMedia;
}
