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
public class GvDataMap<T1 extends GvData, T2 extends GvDataCollection<T1>> implements
        GvDataCollection<T2> {
    public static final Parcelable.Creator<GvDataMap> CREATOR = new Parcelable
            .Creator<GvDataMap>() {
        public GvDataMap createFromParcel(Parcel in) {
            return new GvDataMap(in);
        }

        public GvDataMap[] newArray(int size) {
            return new GvDataMap[size];
        }
    };

    private Map<T1, T2>     mMap;
    private GvDataType<T1> mType;
    private GvReviewId    mReviewId;

    public GvDataMap(GvDataType<T1> dataType, GvReviewId reviewId) {
        mMap = new LinkedHashMap<>();
        mType = dataType;
        mReviewId = reviewId;
    }

    //TODO make type safe
    public GvDataMap(Parcel in) {
        mMap = new LinkedHashMap<>();
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        Class<T1> dataClass = mType.getDataClass();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            T1 key = in.readParcelable(dataClass.getClassLoader());
            T2 value = in.readParcelable(dataClass.getClassLoader());
            mMap.put(key, value);
        }
    }

    public void put(T1 key, T2 datum) {
        mMap.put(key, datum);
    }

    @Override
    public GvDataType<T1> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        int num = size();
        String dataString = num == 1 ? mType.getDatumName() : mType.getDataName();
        return String.valueOf(size()) + " " + dataString;
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
    public T2 getItem(int position) {
        int i = 0;
        for (Map.Entry<T1, T2> entry : mMap.entrySet()) {
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
        for (Map.Entry<T1, T2> entry : mMap.entrySet()) {
            dest.writeParcelable(entry.getKey(), flags);
            dest.writeParcelable(entry.getValue(), flags);
        }
    }
}
