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
public class GvSubjectRatingList extends GvDataList<GvSubjectRatingList
        .GvSubjectRating> {

    GvSubjectRatingList() {
        super(GvType.CHILDREN);
    }

    public boolean contains(String subject) {
        for (GvSubjectRating review : this) {
            if (review.getSubject().equals(subject)) return true;
        }

        return false;
    }

    @Override
    protected Comparator<GvSubjectRating> getDefaultComparator() {

        return new Comparator<GvSubjectRating>() {
            @Override
            public int compare(GvSubjectRating lhs, GvSubjectRating rhs) {
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

    void add(String subject, float rating) {
        add(new GvSubjectRating(subject, rating));
    }

    /**
     * {@link GvDataList.GvData} version of: no equivalent as used
     * for review children (sub-reviews).
     * {@link ViewHolder}: {@link VHReviewNodeSubjectRating}
     */
    public static class GvSubjectRating implements GvDataList.GvData {
        public static final Parcelable.Creator<GvSubjectRating> CREATOR = new Parcelable
                .Creator<GvSubjectRating>() {
            public GvSubjectRating createFromParcel(Parcel in) {
                return new GvSubjectRating(in);
            }

            public GvSubjectRating[] newArray(int size) {
                return new GvSubjectRating[size];
            }
        };
        private final String mSubject;
        private final float  mRating;

        GvSubjectRating(String subject, float rating) {
            mSubject = subject;
            mRating = rating;
        }

        GvSubjectRating(Parcel in) {
            mSubject = in.readString();
            mRating = in.readFloat();
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHReviewNodeSubjectRating();
        }

        @Override
        public boolean isValidForDisplay() {
            return mSubject != null && mSubject.length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvSubjectRating)) return false;

            GvSubjectRating that = (GvSubjectRating) o;

            return Float.compare(that.mRating, mRating) == 0 && !(mSubject != null ? !mSubject
                    .equals(that.mSubject) : that.mSubject != null);

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

        public String getSubject() {
            return mSubject;
        }

        public float getRating() {
            return mRating;
        }
    }
}
