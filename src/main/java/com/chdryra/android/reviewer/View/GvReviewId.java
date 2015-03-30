/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Model.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewId implements GvData {
    public static final Parcelable.Creator<GvReviewId> CREATOR = new Parcelable
            .Creator<GvReviewId>() {
        public GvReviewId createFromParcel(Parcel in) {
            return new GvReviewId(in);
        }

        public GvReviewId[] newArray(int size) {
            return new GvReviewId[size];
        }
    };
    private ReviewId mId;

    public GvReviewId(ReviewId id) {
        mId = id;
    }

    public GvReviewId(Parcel in) {
        mId = ReviewId.generateId(in.readString());
    }

    public String getId() {
        return mId.toString();
    }

    @Override
    public String getStringSummary() {
        return getId();
    }

    @Override
    public boolean hasHoldingReview() {
        return true;
    }

    @Override
    public GvReviewId getHoldingReviewId() {
        return this;
    }

    @Override
    public ViewHolder newViewHolder() {
        return null;
    }

    @Override
    public boolean isValidForDisplay() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvReviewId)) return false;

        GvReviewId that = (GvReviewId) o;

        if (!mId.equals(that.mId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getId());
    }
}
