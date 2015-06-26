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

import java.util.Comparator;

/**
 * Used for review children (sub-reviews).
 */
public class GvChildList extends GvDataList<GvChildList.GvChildReview> {
    public static final GvDataType<GvChildList> TYPE =
            GvTypeMaker.newType(GvChildList.class, GvChildReview.TYPE);

    public GvChildList() {
        super(GvChildReview.class, TYPE, null);
    }

    public GvChildList(GvReviewId id) {
        super(GvChildReview.class, TYPE, id);
    }

    public GvChildList(GvChildList data) {
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

    @Override
    protected Comparator<GvChildReview> getDefaultComparator() {

        return new Comparator<GvChildReview>() {
            @Override
            public int compare(GvChildReview lhs, GvChildReview rhs) {
                int comp = lhs.getSubject().compareTo(rhs.getSubject());
                if (comp == 0) {
                    if (lhs.getRating() < rhs.getRating()) {
                        comp = 1;
                    } else if (lhs.getRating() > rhs.getRating()) {
                        comp = -1;
                    }
                }

                return comp;
            }
        };
    }


    /**
     * {@link GvData} version of: no equivalent as used
     * for review children (sub-reviews).
     * {@link ViewHolder}: {@link VhChild}
     */
    public static class GvChildReview extends GvDataBasic<GvChildReview> {
        public static final GvDataType<GvChildReview> TYPE =
                GvTypeMaker.newType(GvChildReview.class, "criterion", "criteria");
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

            GvChildReview that = (GvChildReview) o;

            if (Float.compare(that.mRating, mRating) != 0) return false;
            if (mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mSubject != null ? mSubject.hashCode() : 0;
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
