package com.odeyalo.sonata.artists.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
@NoArgsConstructor
public class ExceptionMessage {
    // Short description about error
    private String description;
}
