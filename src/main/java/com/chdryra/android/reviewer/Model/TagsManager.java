/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer.Model;

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
        if (sInstance == null) sInstance = new TagsManager();
        return sInstance;
    }

    public static ReviewTagCollection getTags(ReviewId id) {
        ReviewTagCollection tags = getManager().new ReviewTagCollection();
        for (ReviewTag tag : getTags()) {
            if (tag.tagsReview(id)) tags.add(tag);
        }

        return tags;
    }

    public static void tag(ReviewId id, String tag) {
        getManager().tagReview(id, tag);
    }

    public static void tag(ReviewId id, ArrayList<String> tags) {
        for (String tag : tags) {
            getManager().tagReview(id, tag);
        }
    }

    public static boolean untag(ReviewId id, ReviewTag tag) {
        return getManager().untagReview(id, tag);
    }

    private static ReviewTagCollection getTags() {
        return getManager().mTags;
    }

    private void tagReview(ReviewId id, String tag) {
        ReviewTag reviewTag = getTags().get(tag);
        if (reviewTag == null) {
            getTags().add(new ReviewTag(tag, id));
        } else {
            reviewTag.addReview(id);
        }
    }

    private boolean untagReview(ReviewId id, ReviewTag tag) {
        if (tag.tagsReview(id)) {
            tag.removeReview(id);
            if (tag.getReviews().size() == 0) {
                mTags.remove(tag);
                return true;
            }
        }

        return false;
    }

    /**
     * Wraps a string plus a collection of reviews tagged with that string. Comparable with
     * another ReviewTag alphabetically.
     */
    public class ReviewTag implements Comparable<ReviewTag> {
        private final ArrayList<ReviewId> mReviews;
        private final String              mTag;

        private ReviewTag(String tag, ReviewId id) {
            //mTag = WordUtils.capitalize(tag);
            mTag = tag;
            mReviews = new ArrayList<>();
            mReviews.add(id);
        }

        @Override
        public int compareTo(@NotNull ReviewTag another) {
            return mTag.compareToIgnoreCase(another.mTag);
        }

        public String get() {
            return mTag;
        }

        public ArrayList<ReviewId> getReviews() {
            return mReviews;
        }

        public boolean tagsReview(ReviewId id) {
            return mReviews.contains(id);
        }

        public boolean equals(String tag) {
            return mTag.equalsIgnoreCase(tag);
        }

        public boolean equals(ReviewTag tag) {
            return mTag.equals(tag.get()) && mReviews.equals(tag.mReviews);
        }

        private void addReview(ReviewId id) {
            if (!mReviews.contains(id)) mReviews.add(id);
        }

        private void removeReview(ReviewId id) {
            mReviews.remove(id);
        }
    }

    /**
     * Iterable collection of ReviewTags.
     */
    public class ReviewTagCollection implements Iterable<ReviewTag> {
        private final ArrayList<ReviewTag> mTags;

        private ReviewTagCollection() {
            mTags = new ArrayList<>();
        }

        @Override
        public Iterator<ReviewTag> iterator() {
            return new ReviewTagIterator();
        }

        public int size() {
            return mTags.size();
        }

        public ReviewTag getItem(int position) {
            return mTags.get(position);
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

        public class ReviewTagIterator implements Iterator<ReviewTag> {
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
                            "can delete");
                } else {
                    ReviewTagCollection.this.remove(getItem(position));
                }
            }
        }
    }
}
