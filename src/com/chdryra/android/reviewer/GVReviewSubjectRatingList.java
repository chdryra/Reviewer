/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;

/**
 * Used for review children (sub-reviews).
 */
class GVReviewSubjectRatingList extends GVReviewDataList<GVReviewSubjectRatingList
        .GVReviewSubjectRating> {

    GVReviewSubjectRatingList() {
        super(GVType.CHILDREN);
    }

    /**
     * {@link GVReviewData} version of: no equivalent as used for review children (sub-reviews).
     * {@link ViewHolder}: {@link VHReviewNodeSubjectRating}
     */
    static class GVReviewSubjectRating implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVReviewSubjectRating> CREATOR = new Parcelable
                .Creator<GVReviewSubjectRating>() {
            public GVReviewSubjectRating createFromParcel(Parcel in) {
                return new GVReviewSubjectRating(in);
            }

            public GVReviewSubjectRating[] newArray(int size) {
                return new GVReviewSubjectRating[size];
            }
        };
        private final String mSubject;
        private       float  mRating;

        GVReviewSubjectRating(String subject, float rating) {
            mSubject = subject;
            mRating = rating;
        }

        GVReviewSubjectRating(Parcel in) {
            mSubject = in.readString();
            mRating = in.readFloat();
        }

        String getSubject() {
            return mSubject;
        }

        float getRating() {
            return mRating;
        }

        void setRating(float rating) {
            mRating = rating;
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHReviewNodeSubjectRating();
        }

        @Override
        public boolean isValidForDisplay() {
            return mSubject != null && mSubject.length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GVReviewSubjectRating)) return false;

            GVReviewSubjectRating that = (GVReviewSubjectRating) o;

            if (Float.compare(that.mRating, mRating) != 0) return false;
            return !(mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null);

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
            parcel.writeString(mSubject);
            parcel.writeFloat(mRating);
        }
    }

    void add(String subject, float rating) {
        add(new GVReviewSubjectRating(subject, rating));
    }

    public boolean contains(String subject) {
        for (GVReviewSubjectRating review : this) {
            if (review.getSubject().equals(subject)) return true;
        }

        return false;
    }

    @Override
    protected Comparator<GVReviewSubjectRating> getDefaultComparator() {

        return new Comparator<GVReviewSubjectRatingList.GVReviewSubjectRating>() {
            @Override
            public int compare(GVReviewSubjectRating lhs, GVReviewSubjectRating rhs) {
                int comp = lhs.getSubject().compareTo(rhs.getSubject());
                if (comp == 0) {
                    if (lhs.getRating() > rhs.getRating()) {
                        comp = 1;
                    } else if (lhs.getRating() < rhs.getRating()) {
                        comp = -1;
                    }
                }

                return comp;
            }
        };
    }
}
