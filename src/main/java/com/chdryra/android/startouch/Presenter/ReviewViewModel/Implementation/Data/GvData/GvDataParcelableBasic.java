/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataParcelableBasic<T extends GvDataParcelable> extends GvDataBasic<T> implements GvDataParcelable{
    GvDataParcelableBasic(Parcel in) {
        //TODO make type safe
        super((GvDataType<T>) GvDataType.loadFromParcel(in),
                (GvReviewId) in.readParcelable(GvReviewId.class.getClassLoader()));
    }

    GvDataParcelableBasic(GvDataType<T> type) {
        this(type, null);
    }

    GvDataParcelableBasic(GvDataType<T> type, @Nullable GvReviewId reviewId) {
        super(type, reviewId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        GvDataType.writeToParcel(getGvDataType(), parcel);
        parcel.writeParcelable(getGvReviewId(), i);
    }

    @Override
    public GvDataParcelable getParcelable() {
        return this;
    }
}
