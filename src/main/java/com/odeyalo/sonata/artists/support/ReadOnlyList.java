package com.odeyalo.sonata.artists.support;

import java.util.List;

public class ReadOnlyList<E> extends ReadOnlyCollectionAdapter<E> {

    public ReadOnlyList(E... elements) {
        super(List.of(elements));
    }

    public ReadOnlyList(List<E> parent) {
        super(parent);
    }

}
