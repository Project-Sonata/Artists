package com.odeyalo.sonata.artists.service;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Represent basic user inf
 */
@Value
@AllArgsConstructor(staticName = "of")
public class UserInfo {
    String id;
    String email;

    public UserInfo(String id) {
        this.id = id;
        this.email = null;
    }
}
