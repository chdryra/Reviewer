/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

//import org.apache.commons.lang3.text.WordUtils;

/**
 * The singleton that manages the tagging and untagging of Reviews.
 */
public class TagsManager extends ApplicationSingleton {
    private static final String NAME = "TagsManager";
    private static TagsManager sSingleton;

    private final  ReviewTagCollection mTags;

    private TagsManager(Context context) {
        super(context, NAME);
        mTags = new ReviewTagCollection();
    }

    public static TagsManager get(Context c) {
        sSingleton = getSingleton(sSingleton, TagsManager.class, c);
        return sSingleton;
    }

    public static ReviewTagCollection getTags(Context context, ReviewId id) {
        ReviewTagCollection tags = new ReviewTagCollection();
        for (ReviewTag tag : get(context).mTags) {
            if (tag.tagsReview(id)) tags.add(tag);
        }

        return tags;
    }

    public static void tag(Context context, ReviewId id, String tag) {
        get(context).tagReview(id, tag);
    }

    public static void tag(Context context, ReviewId id, ArrayList<String> tags) {
        for (String tag : tags) {
            get(context).tagReview(id, tag);
        }
    }

    public static boolean untag(Context context, ReviewId id, ReviewTag tag) {
        return get(context).untagReview(id, tag);
    }

    public static ReviewTagCollection getTags(Context context) {
        return new ReviewTagCollection(get(context).mTags);
    }

    private void tagReview(ReviewId id, String tag) {
        ReviewTag reviewTag = mTags.get(tag);
        if (reviewTag == null) {
            mTags.add(new ReviewTag(tag, id));
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
     * Iterable collection of ReviewTags.
     */
    public static class ReviewTagCollection implements Iterable<ReviewTag> {
        private final ArrayList<ReviewTag> mTags;

        private ReviewTagCollection() {
            mTags = new ArrayList<>();
        }

        private ReviewTagCollection(ReviewTagCollection tags) {
            mTags = new ArrayList<>(tags.mTags);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ReviewTagCollection)) return false;

            ReviewTagCollection that = (ReviewTagCollection) o;

            return mTags.equals(that.mTags);

        }

        @Override
        public int hashCode() {
            return mTags.hashCode();
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

    /**
     * Wraps a string plus a collection of reviews tagged with that string. Comparable with
     * another ReviewTag alphabetically.
     */
    public class ReviewTag implements Comparable<ReviewTag> {
        private final ArrayList<ReviewId> mReviews;
        private final String mTag;

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
            return new ArrayList<>(mReviews);
        }

        public boolean tagsReview(ReviewId id) {
            return mReviews.contains(id);
        }

        public boolean equals(String tag) {
            return mTag.equalsIgnoreCase(tag);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ReviewTag)) return false;

            ReviewTag reviewTag = (ReviewTag) o;

            if (!mReviews.equals(reviewTag.mReviews)) return false;
            return mTag.equals(reviewTag.mTag);

        }

        private void addReview(ReviewId id) {
            if (!mReviews.contains(id)) mReviews.add(id);
        }

        private void removeReview(ReviewId id) {
            mReviews.remove(id);
        }

        @Override
        public int hashCode() {
            int result = mReviews.hashCode();
            result = 31 * result + mTag.hashCode();
            return result;
        }


    }
}
