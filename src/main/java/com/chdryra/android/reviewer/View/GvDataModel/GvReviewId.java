/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewId implements GvData {
    public static final GvDataType<GvReviewId> TYPE =
            GvTypeMaker.newType(GvReviewId.class, "ReviewId");

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

    private GvReviewId(ReviewId id) {
        mId = id;
    }

    public GvReviewId(GvReviewId id) {
        this(ReviewId.fromString(id.getId()));
    }

    public GvReviewId(Parcel in) {
        mId = ReviewId.fromString(in.readString());
    }

    public static GvReviewId getId(String id) {
        return new GvReviewId(ReviewId.fromString(id));
    }

    public String getId() {
        return mId.toString();
    }

    @Override
    public GvDataType<GvReviewId> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getStringSummary() {
        return getId();
    }

    @Override
    public GvReviewId getReviewId() {
        return this;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public ViewHolder getViewHolder() {
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
