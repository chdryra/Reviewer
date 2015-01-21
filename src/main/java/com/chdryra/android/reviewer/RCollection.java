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
 * An iterable collection of objects that can be referenced using a {@link ReviewId}.
 *
 * @param <T>: object type
 */
public class RCollection<T> implements Iterable<T> {
    public static final String                     NO_ELEMENT    = "No more elements left";
    public static final String                     ILLEGAL_STATE = "Have to do at least one next" +
            "() before you can " +
            "delete";
    private final       LinkedHashMap<ReviewId, T> mData         = new LinkedHashMap<ReviewId, T>();

    @Override
    public Iterator<T> iterator() {
        return new RCollectionIterator();
    }

    public int size() {
        return mData.size();
    }

    public T getItem(int position) {
        return get(getId(position));
    }

    public void put(ReviewId id, T t) {
        if (!containsId(id)) mData.put(id, t);
    }

    public boolean containsId(ReviewId id) {
        return mData.containsKey(id);
    }

    public void add(RCollection<T> items) {
        mData.putAll(items.mData);
    }

    public void remove(ReviewId id) {
        if (containsId(id)) mData.remove(id);
    }

    public T get(ReviewId id) {
        return mData.get(id);
    }

    private ReviewId getId(int position) {
        ReviewId[] keys = mData.keySet().toArray(new ReviewId[mData.size()]);
        return keys[position];
    }

    public class RCollectionIterator implements Iterator<T> {
        private int mPosition = 0;

        @Override
        public boolean hasNext() {
            return mPosition < size() && getItem(mPosition) != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return getItem(mPosition++);
            } else {
                throw new NoSuchElementException(NO_ELEMENT);
            }
        }

        @Override
        public void remove() {
            if (mPosition > 0) {
                RCollection.this.remove(getId(--mPosition));
            } else {
                throw new IllegalStateException(ILLEGAL_STATE);
            }
        }
    }
}
