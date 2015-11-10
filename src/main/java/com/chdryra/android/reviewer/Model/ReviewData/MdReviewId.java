/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

/**
 * Review Data: Wrapper for a UUID
 * <p>
 * Use static methods <code>generateId(.)</code> to return a unique RDId.
 * </p>
 * <p/>
 * <p/>
 * //TODO There's a reason couldn't use holding review but can't remember. Find out.
 */
public class MdReviewId implements MdData {
    private static final String SPLITTER = ":";
    private final String mUserId;
    private long mTime;
    private int mIncrement;
    private String mString;

    /**
     * To facilitate RCollectionReview
     */
    public interface IdAble {
        //abstract
        MdReviewId getMdReviewId();
    }

    public MdReviewId(String userId, long time, int increment) {
        mUserId = userId;
        mTime = time;
        mIncrement = increment;
        mString = mUserId + SPLITTER + String.valueOf(mTime) + SPLITTER +
                String.valueOf(mIncrement);
    }

    public MdReviewId(String rdId) {
        String[] split = rdId.split(SPLITTER);
        mUserId = split[0];
        mTime = Long.parseLong(split[1]);
        mIncrement = Integer.parseInt(split[2]);
        mString = rdId;
    }

    //Overridden
    @Override
    public String getReviewId() {
        return mString;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(mString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdReviewId)) return false;

        MdReviewId mdReviewId = (MdReviewId) o;

        if (mTime != mdReviewId.mTime) return false;
        if (mIncrement != mdReviewId.mIncrement) return false;
        return mUserId.equals(mdReviewId.mUserId);

    }

    @Override
    public int hashCode() {
        int result = mUserId.hashCode();
        result = 31 * result + (int) (mTime ^ (mTime >>> 32));
        result = 31 * result + mIncrement;
        return result;
    }

    public String toString() {
        return mString;
    }
}