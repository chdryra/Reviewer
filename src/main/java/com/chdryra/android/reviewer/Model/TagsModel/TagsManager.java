/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 October, 2014
 */

package com.chdryra.android.reviewer.Model.TagsModel;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

//import org.apache.commons.lang3.text.WordUtils;

/**
 * The singleton that manages the tagging and untagging of Reviews.
 */
public class TagsManager {
    private final ReviewTagCollection mTags;

    //Constructors
    public TagsManager() {
        mTags = new ReviewTagCollection();
    }

    //public methods
    public ReviewTagCollection getTags() {
        return new ReviewTagCollection(mTags);
    }

    public ReviewTagCollection getTags(ReviewId id) {
        ReviewTagCollection tags = new ReviewTagCollection();
        for (ReviewTag tag : mTags) {
            if (tag.tagsReview(id)) tags.add(tag);
        }

        return tags;
    }

    public void tagReview(ReviewId id, ArrayList<String> tags) {
        for (String tag : tags) {
            tagReview(id, tag);
        }
    }

    public void tagReview(ReviewId id, String tag) {
        ReviewTag reviewTag = mTags.get(tag);
        if (reviewTag == null) {
            mTags.add(new ReviewTag(tag, id));
        } else {
            reviewTag.addReview(id);
        }
    }

    public boolean untagReview(ReviewId id, ReviewTag tag) {
        if (tag.tagsReview(id)) {
            tag.removeReview(id);
            if (tag.getReviews().size() == 0) {
                mTags.remove(tag);
                return true;
            }
        }

        return false;
    }

//Classes
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

        public int size() {
            return mTags.size();
        }

        public ReviewTag getItem(int position) {
            return mTags.get(position);
        }

        public ArrayList<String> toStringArray() {
            ArrayList<String> tagArray = new ArrayList<>();
            for (TagsManager.ReviewTag tag : mTags) {
                tagArray.add(tag.get());
            }
            return tagArray;
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

        //Overridden
        @Override
        public Iterator<ReviewTag> iterator() {
            return new ReviewTagIterator();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ReviewTagCollection)) return false;

            ReviewTagCollection that = (ReviewTagCollection) o;

            return mTags.equals(that.mTags);

        }

        public class ReviewTagIterator implements Iterator<ReviewTag> {
            int position = 0;

            //Overridden
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

        @Override
        public int hashCode() {
            return mTags.hashCode();
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

        //public methods
        public ArrayList<ReviewId> getReviews() {
            return new ArrayList<>(mReviews);
        }

        public String get() {
            return mTag;
        }

        public boolean tagsReview(ReviewId id) {
            return mReviews.contains(id);
        }

        public boolean equals(String tag) {
            return mTag.equalsIgnoreCase(tag);
        }

        private void addReview(ReviewId id) {
            if (!mReviews.contains(id)) mReviews.add(id);
        }

        private void removeReview(ReviewId id) {
            mReviews.remove(id);
        }

        //Overridden
        @Override
        public int compareTo(@NotNull ReviewTag another) {
            return mTag.compareToIgnoreCase(another.mTag);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ReviewTag)) return false;

            ReviewTag reviewTag = (ReviewTag) o;

            if (!mReviews.equals(reviewTag.mReviews)) return false;
            return mTag.equals(reviewTag.mTag);

        }


        @Override
        public int hashCode() {
            int result = mReviews.hashCode();
            result = 31 * result + mTag.hashCode();
            return result;
        }


    }
}
