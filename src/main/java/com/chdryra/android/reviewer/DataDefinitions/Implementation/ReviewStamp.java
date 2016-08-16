/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.Validatable;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStamp implements Validatable, ReviewId, HasReviewId {
    private ReviewId mId;
    private AuthorId mAuthorId;
    private DateTime mDate;

    private ReviewStamp() {

    }

    private ReviewStamp(AuthorId authorId, DateTime date) {
        mAuthorId = authorId;
        mDate = date;
        mId = new StampId(mAuthorId.toString(), mDate.getTime());
    }

    public static ReviewStamp newStamp(AuthorId authorId, DateTime date){
        return new ReviewStamp(authorId, date);
    }

    public static ReviewStamp newStamp(AuthorId authorId){
        return new ReviewStamp(authorId, new PublishDate(new Date().getTime()));
    }

    public static ReviewStamp noStamp(){
        return new ReviewStamp();
    }

    public static boolean checkId(ReviewId id) {
        try {
            new StampId(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    public DateTime getDate() {
        return mDate;
    }

    public boolean isValid() {
        return mId != null;
    }

    public String toReadableDate(){
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format
                (new Date(mDate.getTime()));
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(mAuthorId) && validator.validate(mDate) && validator.validate(mId);
    }

    @Override
    public String toString() {
        return mId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewStamp)) return false;

        ReviewStamp stamp = (ReviewStamp) o;

        if (!mAuthorId.equals(stamp.mAuthorId)) return false;
        if (!mDate.equals(stamp.mDate)) return false;
        return mId.equals(stamp.mId);

    }

    @Override
    public int hashCode() {
        int result = mAuthorId.hashCode();
        result = 31 * result + mDate.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }

    private static class StampId implements ReviewId, HasReviewId {
        private static final String SPLITTER = ":";
        private static final String ILLEGAL_FORMAT = "String doesn't conform to UserId:Time";

        private String mId = ":";

        public StampId(@NotNull String userId, long time) {
            if(userId.length() == 0) throwException();
            mId = userId + SPLITTER + String.valueOf(time);
        }

        public StampId(ReviewId id) {
            this(id.toString());
        }

        public StampId(String idString) {
            String[] split = idString.split(SPLITTER);
            if(split.length != 2) throwException(idString);
            try {
                String userId = split[0];
                long time = Long.parseLong(split[1]);
                mId = idString;
                check(userId, time, mId);
            } catch (Exception e) {
                throwException("On id: " + idString, e);
            }
        }

        private static void check(@NonNull String userId, long time, String idString) {
            boolean correct = new StampId(userId, time).toString().equals(idString);
            if(!correct) throwException(idString);
        }

        private static void throwException(String id, Throwable cause) {
            throw new IllegalArgumentException(id, cause);
        }

        private static void throwException(String idString) {
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
}
