package com.odeyalo.sonata.artists.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ReadOnlyCollectionAdapter<E> implements ReadOnlyCollection<E> {
    private final List<E> parent;

    public ReadOnlyCollectionAdapter(Collection<E> parent) {
        if (parent == null) {
            parent = new ArrayList<>();
        }
        this.parent = new ArrayList<>(parent);
    }

    @Override
    public int size() {
        return parent.size();
    }

    @Override
    public boolean isEmpty() {
        return parent.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return parent.iterator();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return parent.containsAll(c);
    }

    @Override
    public E get(int index) {
        return parent.get(index);
    }

    @Override
    public boolean contains(Object o) {
        return parent.contains(o);
    }
}
