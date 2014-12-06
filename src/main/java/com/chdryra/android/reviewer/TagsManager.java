/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

//import org.apache.commons.lang3.text.WordUtils;

/**
 * The singleton that manages the tagging and untagging of Reviews.
 */
public class TagsManager {
    private static TagsManager         sInstance;
    private final  ReviewTagCollection mTags;

    private TagsManager() {
        mTags = new ReviewTagCollection();
    }

    private static TagsManager getManager() {
        if (sInstance == null) {
            sInstance = new TagsManager();
        }

        return sInstance;
    }

    private static ReviewTagCollection getTags() {
        return getManager().mTags;
    }

    static ReviewTagCollection getTags(Review review) {
        ReviewTagCollection tags = getManager().new ReviewTagCollection();
        for (ReviewTag tag : getTags()) {
            if (tag.tagsReview(review)) {
                tags.add(tag);
            }
        }

        return tags;
    }

    static void tag(Review review, GVTagList tags) {
        for (GVTagList.GVTag tag : tags) {
            getManager().tag(review, tag.get());
        }
    }

    static void untag(Review review, ReviewTag tag) {
        tag.removeReview(review);
        if (!tag.isValid()) {
            getTags().remove(tag);
        }
    }

    private void tag(Review review, String tag) {
        ReviewTag reviewTag = getTags().get(tag);
        if (reviewTag == null) {
            reviewTag = getManager().new ReviewTag(tag, review);
            getTags().add(reviewTag);
        } else {
            reviewTag.addReview(review);
        }
    }

    /**
     * Wraps a string plus a collection of reviews tagged with that string. Comparable with
     * another ReviewTag alphabetically.
     */
    class ReviewTag implements Comparable<ReviewTag> {
        private final RCollectionReview<Review> mReviews;
        private final String                    mTag;

        private ReviewTag(String tag, Review review) {
            //mTag = WordUtils.capitalize(tag);
            mTag = tag;
            mReviews = new RCollectionReview<Review>();
            mReviews.add(review);
        }

        @Override
        public int compareTo(@NotNull ReviewTag another) {
            return mTag.compareToIgnoreCase(another.mTag);
        }

        String get() {
            return mTag;
        }

        boolean tagsReview(Review r) {
            return mReviews.containsId(r.getId());
        }

        boolean equals(String tag) {
            return mTag.equalsIgnoreCase(tag);
        }

        private boolean isValid() {
            return mReviews.size() > 0;
        }

        private void addReview(Review review) {
            mReviews.add(review);
        }

        private void removeReview(Review review) {
            mReviews.remove(review.getId());
        }
    }

    /**
     * Iterable collection of ReviewTags.
     */
    class ReviewTagCollection implements Iterable<ReviewTag> {
        private final ArrayList<ReviewTag> mTags;

        private ReviewTagCollection() {
            mTags = new ArrayList<ReviewTag>();
        }

        @Override
        public Iterator<ReviewTag> iterator() {
            return new ReviewTagIterator();
        }

        int size() {
            return mTags.size();
        }

        private ReviewTag get(String tag) {
            ReviewTag rTag = null;
            for (ReviewTag reviewTag : mTags) {
                if (reviewTag.equals(tag)) {
                    rTag = reviewTag;
                    break;
                }
            }

            return rTag;
        }

        private void add(ReviewTag tag) {
            if (!mTags.contains(tag)) {
                mTags.add(tag);
            }
        }

        private void remove(ReviewTag tag) {
            mTags.remove(tag);
        }

        private ReviewTag getItem(int position) {
            return mTags.get(position);
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
                    throw new IllegalStateException("Have to do at least one next() before you " +
                            "can " +
                            "delete");
                } else {
                    ReviewTagCollection.this.remove(getItem(position));
                }
            }
        }
    }
}
