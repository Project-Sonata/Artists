package com.odeyalo.sonata.artists.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class SocialMedia {
    String platform;
    String link;
}
