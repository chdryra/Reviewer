/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

/**
 * An iterable collection of objects that can be referenced using an {@link RDId}.
 *
 * @param <T>: object type
 */
class RCollection<T> implements Iterable<T> {
    private final LinkedHashMap<RDId, T> mData = new LinkedHashMap<RDId, T>();

    RCollection() {
    }

    class CollectionIterator implements Iterator<T> {
        int position = 0;

        @Override
        public boolean hasNext() {
            return position < size() && getItem(position) != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return getItem(position++);
            } else {
                throw new NoSuchElementException("No more elements left");
            }
        }

        @Override
        public void remove() {
            if (position <= 0) {
                throw new IllegalStateException("Have to do at least one next() before you can " +
                                                "delete");
            } else {
                RCollection.this.remove(getId(position));
            }
        }
    }

    public void put(RDId id, T t) {
        if (!containsId(id)) {
            mData.put(id, t);
        }
    }

    public boolean containsId(RDId id) {
        return mData.containsKey(id);
    }

    public void add(RCollection<T> items) {
        mData.putAll(items.mData);
    }

    public void remove(RDId id) {
        if (containsId(id)) {
            mData.remove(id);
        }
    }

    public int size() {
        return mData.size();
    }

    T getItem(int position) {
        return get(getId(position));
    }

    public T get(RDId id) {
        return mData.get(id);
    }

    RDId getId(int position) {
        RDId[] keys = mData.keySet().toArray(new RDId[mData.size()]);
        return keys[position];
    }

    @Override
    public Iterator<T> iterator() {
        return new CollectionIterator();
    }

}
