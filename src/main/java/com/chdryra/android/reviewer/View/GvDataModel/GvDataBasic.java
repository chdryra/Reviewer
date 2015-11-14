/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataBasic<T extends GvData> implements GvData {
    private GvDataType<T> mType;
    private GvReviewId mReviewId;

    //Constructors
    public GvDataBasic(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    protected GvDataBasic(GvDataType<T> type) {
        mType = type;
    }

    protected GvDataBasic(GvDataType<T> type, GvReviewId reviewId) {
        mType = type;
        mReviewId = reviewId;
    }

    //Overridden
    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mType, i);
        parcel.writeParcelable(mReviewId, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataBasic)) return false;

        GvDataBasic<?> that = (GvDataBasic<?>) o;

        if (!mType.equals(that.mType)) return false;
        return !(mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null);

    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
