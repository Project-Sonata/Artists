package com.odeyalo.sonata.artists.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SocialMediaDto {
    String platform;
    String link;
}
