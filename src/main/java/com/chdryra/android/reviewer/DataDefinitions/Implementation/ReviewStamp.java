/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
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
    private DataAuthor mAuthor;
    private DataDate mDate;
    private ReviewId mId;

    private ReviewStamp() {

    }

    private ReviewStamp(DataAuthor author, DataDate date) {
        mAuthor = author;
        mDate = date;
        mId = new StampId(author.getAuthorId().toString(), date.getTime());
    }

    public static ReviewStamp newStamp(DataAuthor author, DataDate date){
        return new ReviewStamp(author, date);
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

    public DataAuthor getAuthor() {
        return mAuthor;
    }

    public DataDate getDate() {
        return mDate;
    }

    public boolean isValid() {
        return mId != null;
    }

    public String toReadable(){
        String author = mAuthor.getName();
        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format
                (new Date(mDate.getTime()));
        return date + " by " + author;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(mAuthor) && validator.validate(mDate) && validator.validate(mId);
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

        if (!mAuthor.equals(stamp.mAuthor)) return false;
        if (!mDate.equals(stamp.mDate)) return false;
        return mId.equals(stamp.mId);

    }

    @Override
    public int hashCode() {
        int result = mAuthor.hashCode();
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
