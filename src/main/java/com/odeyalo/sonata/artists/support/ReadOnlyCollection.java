package com.odeyalo.sonata.artists.support;

import java.util.Collection;
import java.util.Iterator;

/**
 * Represent the list that used only for reading
 */
public interface ReadOnlyCollection<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    Iterator<E> iterator();

    boolean containsAll(Collection<?> c);

    E get(int index);

    boolean contains(Object o);

}
