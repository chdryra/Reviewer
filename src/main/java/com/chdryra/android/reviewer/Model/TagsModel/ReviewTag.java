package com.chdryra.android.reviewer.Model.TagsModel;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Wraps a string plus a collection of reviews tagged with that string. Comparable with
 * another ReviewTag alphabetically.
 */
public class ReviewTag implements Comparable<ReviewTag> {
    private final ArrayList<ReviewId> mReviews;
    private final String mTag;

    ReviewTag(String tag, ReviewId id) {
        //mTag = WordUtils.capitalize(tag);
        mTag = tag;
        mReviews = new ArrayList<>();
        mReviews.add(id);
    }

    //public methods
    public ArrayList<ReviewId> getReviews() {
        return new ArrayList<>(mReviews);
    }

    public String getTag() {
        return mTag;
    }

    public boolean tagsReview(ReviewId id) {
        return mReviews.contains(id);
    }

    public boolean equals(String tag) {
        return mTag.equalsIgnoreCase(tag);
    }

    void addReview(ReviewId id) {
        if (!mReviews.contains(id)) mReviews.add(id);
    }

    void removeReview(ReviewId id) {
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
