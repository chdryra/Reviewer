/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.ReviewTagsManager.ReviewTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReviewTagCollection implements Iterable<ReviewTag> {

    private final ArrayList<ReviewTag> mTags;

    public ReviewTagCollection() {
        mTags = new ArrayList<ReviewTag>();
    }

    public void add(ReviewTag tag) {
        if (!mTags.contains(tag)) {
            mTags.add(tag);
        }
    }

    void remove(ReviewTag tag) {
        mTags.remove(tag);
    }

    public ReviewTag get(String tag) {
        ReviewTag rTag = null;
        for (ReviewTag reviewTag : mTags) {
            if (reviewTag.equals(tag)) {
                rTag = reviewTag;
                break;
            }
        }

        return rTag;
    }

    int size() {
        return mTags.size();
    }

    ReviewTag getItem(int position) {
        return mTags.get(position);
    }

    @Override
    public Iterator<ReviewTag> iterator() {
        return new ReviewTagIterator();
    }

    class ReviewTagIterator implements Iterator<ReviewTag> {
        int position = 0;

        @Override
        public boolean hasNext() {
            return position < size() && getItem(position) != null;
        }

        @Override
        public ReviewTag next() {
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
                ReviewTagCollection.this.remove(getItem(position));
            }
        }
    }
}