/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

/**
 * Review Data: Wrapper for a UUID
 * <p>
 * Use static methods <code>generateId(.)</code> to return a unique RDId.
 * </p>
 * <p/>
 * <p>
 * {@link #hasData()}: true
 * </p>
 * <p/>
 * //TODO There's a reason couldn't use holding review but can't remember. Find out.
 */
public class ReviewId implements MdData {
    private static final String SPLITTER = ":";
    private final UserId mId;
    private       long   mTime;
    private int mIncrement;
    private String mString;

    private ReviewId(UserId id, long time, int increment) {
        mId = id;
        mTime = time;
        mIncrement = increment;
        mString = mId.toString() + SPLITTER + String.valueOf(mTime) + SPLITTER +
                String.valueOf(increment);
    }

    private ReviewId(String rdId) {
        String[] split = rdId.split(SPLITTER);
        mId = UserId.fromString(split[0]);
        mTime = Long.parseLong(split[1]);
        mIncrement = Integer.parseInt(split[2]);
        mString = rdId;
    }

    public static ReviewPublisher newPublisher(Author author, PublishDate date) {
        return new ReviewPublisher(author, date);
    }

    public static ReviewId fromString(String rdId) {
        return new ReviewId(rdId);
    }

    @Override
    public ReviewId getReviewId() {
        return this;
    }

    @Override
    public boolean hasData() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;

        ReviewId reviewId = (ReviewId) o;

        if (mTime != reviewId.mTime) return false;
        if (mIncrement != reviewId.mIncrement) return false;
        return mId.equals(reviewId.mId);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        result = 31 * result + mIncrement;
        return result;
    }

    public String toString() {
        return mString;
    }

    /**
     * To facilitate RCollectionReview
     */
    public interface IdAble {
        ReviewId getId();
    }

    public static class ReviewPublisher {
        private Author mAuthor;
        private PublishDate mDate;
        private int mIndex;

        private ReviewPublisher(Author author, PublishDate date) {
            mAuthor = author;
            mDate = date;
        }

        public Author getAuthor() {
            return mAuthor;
        }

        public PublishDate getDate() {
            return mDate;
        }

        public ReviewId nextId() {
            return new ReviewId(mAuthor.getUserId(), mDate.getTime(), mIndex++);
        }
    }
}