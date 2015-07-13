/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 13/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMap<T extends GvData> implements GvDataCollection<T> {
    public static final Parcelable.Creator<GvDataMap> CREATOR = new Parcelable
            .Creator<GvDataMap>() {
        public GvDataMap createFromParcel(Parcel in) {
            return new GvDataMap(in);
        }

        public GvDataMap[] newArray(int size) {
            return new GvDataMap[size];
        }
    };

    private Map<T, T>     mMap;
    private GvDataType<T> mType;
    private GvReviewId    mReviewId;

    public GvDataMap(GvDataType<T> dataType, GvReviewId reviewId) {
        mMap = new LinkedHashMap<>();
        mType = dataType;
        mReviewId = reviewId;
    }

    //TODO make type safe
    public GvDataMap(Parcel in) {
        mMap = new LinkedHashMap<>();
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        Class<T> dataClass = mType.getDataClass();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            T key = in.readParcelable(dataClass.getClassLoader());
            T value = in.readParcelable(dataClass.getClassLoader());
            mMap.put(key, value);
        }
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return null;
    }

    @Override
    public GvReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasElements() {
        return size() > 0;
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public int size() {
        return mMap.size();
    }

    @Override
    public void sort() {

    }

    @Override
    public T getItem(int position) {
        int i = 0;
        for (Map.Entry<T, T> entry : mMap.entrySet()) {
            if (position == i++) return entry.getValue();
        }

        return null;
    }

    @Override
    public ViewHolder getViewHolder() {
        return null;
    }

    @Override
    public boolean isValidForDisplay() {
        return size() > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMap.size());
        dest.writeParcelable(mReviewId, flags);
        dest.writeParcelable(mType, flags);
        for (Map.Entry<T, T> entry : mMap.entrySet()) {
            dest.writeParcelable(entry.getKey(), flags);
            dest.writeParcelable(entry.getValue(), flags);
        }
    }
}
