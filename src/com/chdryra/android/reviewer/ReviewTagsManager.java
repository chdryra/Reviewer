/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//import org.apache.commons.lang3.text.WordUtils;

public class ReviewTagsManager {
    private static ReviewTagsManager   sInstance;
    private final  ReviewTagCollection mTags;

    private ReviewTagsManager() {
        mTags = new ReviewTagCollection();
    }

    public static ReviewTagCollection getTags(Review review) {
        ReviewTagCollection tags = new ReviewTagCollection();
        for (ReviewTag tag : getTags()) {
            if (tag.hasReview(review)) {
                tags.add(tag);
            }
        }

        return tags;
    }

    private static ReviewTagCollection getTags() {
        return getManager().mTags;
    }

    private static ReviewTagsManager getManager() {
        if (sInstance == null) {
            sInstance = new ReviewTagsManager();
        }

        return sInstance;
    }

    public static void tag(Review review, ArrayList<String> tags) {
        for (String tag : tags) {
            getManager().tag(review, tag);
        }
    }

    void tag(Review review, String tag) {
        ReviewTag reviewTag = getTags().get(tag);
        if (reviewTag == null) {
            reviewTag = getManager().new ReviewTag(tag, review);
            getTags().add(reviewTag);
        } else {
            reviewTag.addReview(review);
        }
    }

    public void untag(Review review, ReviewTag tag) {
        tag.removeReview(review);
        if (!tag.isValid()) {
            mTags.remove(tag);
        }
    }

    public class ReviewTag implements Comparable<ReviewTag> {
        private final RCollectionReview<Review> mReviews;
        private final String            mTag;

        private ReviewTag(String tag, Review review) {
            //mTag = WordUtils.capitalize(tag);
            mTag = tag;
            mReviews = new RCollectionReview<Review>();
            mReviews.add(review);
        }

        public String toString() {
            return mTag;
        }

        public boolean hasReview(Review r) {
            return mReviews.containsId(r.getId());
        }

        public boolean equals(String tag) {
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

        @Override
        public int compareTo(@NotNull ReviewTag another) {
            return mTag.compareToIgnoreCase(another.mTag);
        }
    }

}
