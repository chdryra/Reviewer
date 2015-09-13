/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.Utils.RatingFormatter;

/**
 * Used for review children (sub-reviews).
 */
public class GvChildReviewList extends GvDataList<GvChildReviewList.GvChildReview> {
    public GvChildReviewList() {
        super(GvChildReview.TYPE, null);
    }

    public GvChildReviewList(GvReviewId id) {
        super(GvChildReview.TYPE, id);
    }

    public GvChildReviewList(GvChildReviewList data) {
        super(data);
    }

    public boolean contains(String subject) {
        for (GvChildReview review : this) {
            if (review.getSubject().equals(subject)) return true;
        }

        return false;
    }

    public float getAverageRating() {
        float rating = 0;
        for (GvChildReview review : this) {
            rating += review.getRating() / size();
        }

        return rating;
    }

    /**
     * {@link GvData} version of: no equivalent as used
     * for review children (sub-reviews).
     * {@link ViewHolder}: {@link VhChild}
     */
    public static class GvChildReview extends GvDataBasic<GvChildReview> {
        public static final GvDataType<GvChildReview> TYPE =
                new GvDataType<>(GvChildReview.class, "criterion", "criteria");
        public static final Parcelable.Creator<GvChildReview> CREATOR = new Parcelable
                .Creator<GvChildReview>() {
            public GvChildReview createFromParcel(Parcel in) {
                return new GvChildReview(in);
            }

            public GvChildReview[] newArray(int size) {
                return new GvChildReview[size];
            }
        };

        private final String mSubject;
        private final float  mRating;

        public GvChildReview() {
            this(null, 0f);
        }

        public GvChildReview(String subject, float rating) {
            this(null, subject, rating);
        }

        public GvChildReview(GvReviewId id, String subject, float rating) {
            super(GvChildReview.TYPE, id);
            mSubject = subject;
            mRating = rating;
        }

        public GvChildReview(GvChildReview child) {
            this(child.getReviewId(), child.getSubject(), child.getRating());
        }

        GvChildReview(Parcel in) {
            super(in);
            mSubject = in.readString();
            mRating = in.readFloat();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhChild();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validateString(mSubject);
        }

        @Override
        public String getStringSummary() {
            return getSubject() + ": " + RatingFormatter.outOfFive(getRating());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvChildReview)) return false;
            if (!super.equals(o)) return false;

            GvChildReview that = (GvChildReview) o;

            if (Float.compare(that.mRating, mRating) != 0) return false;
            return !(mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
            result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(mSubject);
            parcel.writeFloat(mRating);
        }

        public String getSubject() {
            return mSubject;
        }

        public float getRating() {
            return mRating;
        }
    }
}
