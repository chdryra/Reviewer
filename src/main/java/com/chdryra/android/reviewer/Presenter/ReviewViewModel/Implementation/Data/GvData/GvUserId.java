/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhText;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvUserId implements GvData, UserId {
    public static final GvDataType<GvUserId> TYPE =
            new GvDataType<>(GvUserId.class, "ReviewId");

    public static final Creator<GvUserId> CREATOR = new Creator<GvUserId>() {
        @Override
        public GvUserId createFromParcel(Parcel in) {
            return new GvUserId(in);
        }

        @Override
        public GvUserId[] newArray(int size) {
            return new GvUserId[size];
        }
    };

    private GvReviewId mReviewId;
    private String mUserId;

    public GvUserId(GvReviewId reviewId, String userId) {
        mReviewId = reviewId;
        mUserId = userId;
    }

    public GvUserId(Parcel in) {
        mUserId = in.readString();
    }

    @Override
    public String toString() {
        return mUserId;
    }

    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }

    @Override
    public GvDataType<GvUserId> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getStringSummary() {
        return toString();
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
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
        return dataValidator.validateString(mUserId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvUserId)) return false;

        GvUserId that = (GvUserId) o;

        if (!mUserId.equals(that.mUserId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mUserId.hashCode();
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
