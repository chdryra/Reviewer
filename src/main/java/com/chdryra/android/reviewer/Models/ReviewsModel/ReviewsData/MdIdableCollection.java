/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A list for objects that have their own reference {@link MdReviewId}
 * such as {@link Review}s and {@link MdData}.
 *
 */
public class MdIdableCollection<T extends DataReview> implements IdableCollection<T> {
    public static final String NO_ELEMENT = "No more elements left";
    public static final String ILLEGAL_STATE = "Have to do at least one next() before " +
            "you can delete";
    private final Map<String, T> mData = new LinkedHashMap<>();

    public int size() {
        return mData.size();
    }

    public T getItem(int position) {
        return get(getId(position));
    }

    public void add(T item) {
        if (!containsId(item.getReviewId())) mData.put(item.getReviewId(), item);
    }

    public boolean containsId(String id) {
        return mData.containsKey(id);
    }

    public void addCollection(IdableCollection<T> items) {
        for(T item : items) {
            mData.put(item.getReviewId(), item);
        }
    }

    public void remove(String id) {
        if (containsId(id)) mData.remove(id);
    }

    public T get(String id) {
        return mData.get(id);
    }

    private String getId(int position) {
        String[] keys = mData.keySet().toArray(new String[mData.size()]);
        return keys[position];
    }

    //Overridden
    @Override
    public Iterator<T> iterator() {
        return new ReviewIdableListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdIdableCollection)) return false;

        MdIdableCollection<?> that = (MdIdableCollection<?>) o;

        return mData.equals(that.mData);

    }

    @Override
    public int hashCode() {
        return mData.hashCode();
    }

    public class ReviewIdableListIterator implements Iterator<T> {
        private int mPosition = 0;

        //Overridden
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
                MdIdableCollection.this.remove(getId(--mPosition));
            } else {
                throw new IllegalStateException(ILLEGAL_STATE);
            }
        }
    }
}
