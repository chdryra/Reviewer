/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhText;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewId implements GvData, ReviewId {
    public static final GvDataType<GvReviewId> TYPE =
            new GvDataType<>(GvReviewId.class, "ReviewId");

    public static final Parcelable.Creator<GvReviewId> CREATOR = new Parcelable
            .Creator<GvReviewId>() {
        @Override
        public GvReviewId createFromParcel(Parcel in) {
            return new GvReviewId(in);
        }

        @Override
        public GvReviewId[] newArray(int size) {
            return new GvReviewId[size];
        }
    };

    private String mId;

    public GvReviewId(ReviewId id) {
        this(id.toString());
    }

    public GvReviewId(Parcel in) {
        mId = in.readString();
    }

    public GvReviewId(String id) {
        mId = id;
    }

    @Override
    public String toString() {
        return mId;
    }

    @Override
    public GvReviewId getGvReviewId() {
        return this;
    }

    @Override
    public GvDataType<GvReviewId> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getStringSummary() {
        return toString();
    }

    @Override
    public ReviewId getReviewId() {
        return this;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isVerboseCollection() {
        return false;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhText();
    }

    @Override
    public boolean isValidForDisplay() {
        return false;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(mId);
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
        parcel.writeString(toString());
    }
}
