package com.odeyalo.sonata.artists.support;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadOnlySet<E> extends ReadOnlyCollectionAdapter<E> {

    public ReadOnlySet(E... elements) {
        super(Set.of(elements));
    }

    public ReadOnlySet(Set<E> parent) {
        super(parent);
    }

    public ReadOnlySet(List<E> parent) {
        this(new HashSet<>(parent));
    }
}
