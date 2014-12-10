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
 * An iterable collection of objects that can be referenced using an {@link ReviewId}.
 *
 * @param <T>: object type
 */
class RCollection<T> implements Iterable<T> {
    private final LinkedHashMap<ReviewId, T> mData = new LinkedHashMap<ReviewId, T>();

    @Override
    public Iterator<T> iterator() {
        return new CollectionIterator();
    }

    void put(ReviewId id, T t) {
        if (!containsId(id)) {
            mData.put(id, t);
        }
    }

    boolean containsId(ReviewId id) {
        return mData.containsKey(id);
    }

    void add(RCollection<T> items) {
        mData.putAll(items.mData);
    }

    void remove(ReviewId id) {
        if (containsId(id)) {
            mData.remove(id);
        }
    }

    int size() {
        return mData.size();
    }

    T getItem(int position) {
        return get(getId(position));
    }

    T get(ReviewId id) {
        return mData.get(id);
    }

    ReviewId getId(int position) {
        ReviewId[] keys = mData.keySet().toArray(new ReviewId[mData.size()]);
        return keys[position];
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

}
