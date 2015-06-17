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
public abstract class GvDataBasic implements GvData {
    private GvDataType mType;
    private GvReviewId mReviewId;

    protected GvDataBasic(GvDataType type) {
        mType = type;
    }

    protected GvDataBasic(GvDataType type, GvReviewId reviewId) {
        mType = type;
        mReviewId = reviewId;
    }

    public GvDataBasic(Parcel in) {
        mType = (GvDataType) in.readSerializable();
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    @Override
    public GvDataType getGvDataType() {
        return mType;
    }

    @Override
    public GvReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(mType);
        parcel.writeParcelable(mReviewId, i);
    }
}
