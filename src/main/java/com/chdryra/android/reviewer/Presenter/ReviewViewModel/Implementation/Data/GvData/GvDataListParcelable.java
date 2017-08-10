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
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class GvDataListParcelable<T extends GvDataParcelable> extends GvDataListImpl<T> implements GvDataParcelable{
    public static final Parcelable.Creator<GvDataList> CREATOR = new Parcelable
            .Creator<GvDataList>() {
        @Override
        public GvDataList createFromParcel(Parcel in) {
            return new GvDataListParcelable(in);
        }

        @Override
        public GvDataList[] newArray(int size) {
            return new GvDataList[size];
        }
    };

    //Constructors
    private GvDataListParcelable(GvReviewId reviewId, GvDataList<T> data) {
        super(reviewId, data);
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return this;
    }

    GvDataListParcelable(@NotNull GvDataType<T> type, GvReviewId reviewId) {
        super(type, reviewId);
    }

    //Copy constructor
    GvDataListParcelable(GvDataList<T> data) {
        this(data.getGvReviewId(), data);
    }

    //TODO make type safe
    GvDataListParcelable(Parcel in) {
        super((GvDataType<T>)GvDataType.loadFromParcel(in),
                (GvReviewId)in.readParcelable(GvReviewId.class.getClassLoader()));
        T[] data = (T[]) in.readParcelableArray(getGvDataType().getDataClass().getClassLoader());
        addAll(new ArrayList<>(Arrays.asList(data)));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        GvDataType.writeToParcel(getGvDataType(), dest);
        //TODO make type safe
        dest.writeParcelableArray((T[]) toArray(), flags);
        dest.writeParcelable(getGvReviewId(), flags);
    }
}
