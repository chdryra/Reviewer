/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvReviewId implements GvData {
    public static final GvReviewId NULL_ID = new GvReviewId();
    public static final GvDataType<GvReviewId> TYPE =
            new GvDataType<>(GvReviewId.class, "ReviewId");

    public static final Parcelable.Creator<GvReviewId> CREATOR = new Parcelable
            .Creator<GvReviewId>() {
        //Overridden
        public GvReviewId createFromParcel(Parcel in) {
            return new GvReviewId(in);
        }

        public GvReviewId[] newArray(int size) {
            return new GvReviewId[size];
        }
    };
    private String mId;

    //Constructors
    private GvReviewId() {

    }

    public GvReviewId(GvReviewId id) {
        this(id.getId());
    }

    public GvReviewId(Parcel in) {
        mId = in.readString();
    }

    public GvReviewId(String id) {
        mId = id;
    }

    //public methods
    public String getId() {
        return mId;
    }

    //Overridden

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
        return getId();
    }

    @Override
    public String getReviewId() {
        return toString();
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
        return null;
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
        parcel.writeString(getId());
    }
}
