/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

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
    private static final String ILLEGAL_FORMAT = "String doesn't conform to UserId:Time:Increment!";

    private String mId;

    public MdReviewId(@NotNull String userId, long time, int increment) {
        if(userId.length() == 0) throwException();
        mId = userId + SPLITTER + String.valueOf(time) + SPLITTER +
                String.valueOf(increment);
    }

    public MdReviewId(ReviewId id) {
        this(id.toString());
    }

    public MdReviewId(String idString) {
        String[] split = idString.split(SPLITTER);
        if(split.length != 3) throwException(idString);
        try {
            String userId = split[0];
            long time = Long.parseLong(split[1]);
            int increment = Integer.parseInt(split[2]);
            mId = idString;
            check(userId, time, increment, mId);
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
        if (!(o instanceof ReviewId)) return false;

        ReviewId that = (ReviewId) o;

        return mId.equals(that.toString());
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public String toString() {
        return mId;
    }
}