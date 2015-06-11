/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Model.ReviewStructure.Review;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A list for objects that have their own reference {@link ReviewId}
 * such as {@link Review}s and {@link MdData}.
 *
 * @param <T>: type that is {@link ReviewId.ReviewIdAble}
 */
public class ReviewIdableList<T extends ReviewId.ReviewIdAble> implements Iterable<T> {
    public static final String           NO_ELEMENT    = "No more elements left";
    public static final String           ILLEGAL_STATE = "Have to do at least one next() before " +
            "you can delete";
    private final       Map<ReviewId, T> mData         = new LinkedHashMap<>();

    @Override
    public Iterator<T> iterator() {
        return new ReviewIdableListIterator();
    }

    public int size() {
        return mData.size();
    }

    public T getItem(int position) {
        return get(getId(position));
    }

    public void add(T item) {
        if (!containsId(item.getId())) mData.put(item.getId(), item);
    }

    public boolean containsId(ReviewId id) {
        return mData.containsKey(id);
    }

    public void add(ReviewIdableList<T> items) {
        mData.putAll(items.mData);
    }

    public void remove(ReviewId id) {
        if (containsId(id)) mData.remove(id);
    }

    public T get(ReviewId id) {
        return mData.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewIdableList)) return false;

        ReviewIdableList<?> that = (ReviewIdableList<?>) o;

        return mData.equals(that.mData);

    }

    @Override
    public int hashCode() {
        return mData.hashCode();
    }

    private ReviewId getId(int position) {
        ReviewId[] keys = mData.keySet().toArray(new ReviewId[mData.size()]);
        return keys[position];
    }

    public class ReviewIdableListIterator implements Iterator<T> {
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
                ReviewIdableList.this.remove(getId(--mPosition));
            } else {
                throw new IllegalStateException(ILLEGAL_STATE);
            }
        }
    }
}
