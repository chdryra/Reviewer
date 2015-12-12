/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import org.jetbrains.annotations.NotNull;

/**
 * Review Data: Wrapper for a UUID
 * <p>
 * Use static methods <code>generateId(.)</code> to return a unique RDId.
 * </p>
 * <p/>
 * <p/>
 */
public class MdReviewId implements ReviewId, HasReviewId {
    private static final String SPLITTER = ":";
    private static final String ILLEGAL_FORMAT = "String doesn't conform to UserId:Time:Increment" +
            " format!";
    private String mUserId;
    private long mTime;
    private int mIncrement;
    private String mString;

    public MdReviewId(@NotNull String userId, long time, int increment) {
        if(userId.length() == 0) throwException();
        mUserId = userId;
        mTime = time;
        mIncrement = increment;
        mString = mUserId + SPLITTER + String.valueOf(mTime) + SPLITTER +
                String.valueOf(mIncrement);
    }

    public MdReviewId(ReviewId id) {
        this(id.toString());
    }

    public MdReviewId(String idString) {
        String[] split = idString.split(SPLITTER);
        if(split.length != 3) throwException(idString);
        try {
            mUserId = split[0];
            mTime = Long.parseLong(split[1]);
            mIncrement = Integer.parseInt(split[2]);
            mString = idString;
            check(mUserId, mTime, mIncrement, mString);
        } catch (Exception e) {
            throwException("On id: " + idString, e);
        }
    }

    private void check(@NonNull String userId, long time, int increment, String idString) {
        boolean correct = new MdReviewId(userId, time, increment).toString().equals(idString);
        if(!correct) throwException(idString);
    }

    private void throwException(String id, Throwable cause) {
        throw new IllegalArgumentException(id, cause);
    }

    private void throwException(String idString) {
        throwException("On id: " + idString, new Throwable(ILLEGAL_FORMAT));
    }

    private void throwException() {
        throw new IllegalArgumentException(ILLEGAL_FORMAT);
    }

    @Override
    public ReviewId getReviewId() {
        return this;
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