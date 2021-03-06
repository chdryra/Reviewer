/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhText;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewId implements GvDataParcelable, ReviewId {
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
    private static final GvDataType<GvReviewId> TYPE =
            new GvDataType<>(GvReviewId.class, TYPE_NAME);
    private String mId;

    public GvReviewId() {
        mId = "";
    }

    public GvReviewId(ReviewId id) {
        this(id.toString());
    }

    public GvReviewId(Parcel in) {
        mId = in.readString();
    }

    public GvReviewId(String id) {
        mId = id;
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return this;
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
    public ReviewId getReviewId() {
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
        if (!(o instanceof ReviewId)) return false;

        ReviewId that = (ReviewId) o;

        return mId.equals(that.toString());
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
