/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhText;

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

    private DataRating mRating;

    public GvReviewId(DataRating rating) {
        mRating = rating;
    }

    public GvReviewId(Parcel in) {
        mRating = new DatumRating(new DatumReviewId(in.readString()), in.readFloat(), in.readInt());
    }

    public GvReviewId(String id) {

    }

    @Override
    public String toString() {
        return mRating.getReviewId().toString();
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

    public DataRating getRating() {
        return mRating;
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
        return dataValidator.validate(mRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mRating.getReviewId().toString());
        parcel.writeFloat(mRating.getRating());
        parcel.writeInt(mRating.getRatingWeight());
    }
}
