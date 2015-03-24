/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The View layer (V) data equivalent of the Model layer (M) data {@link MdDataList}.
 * Implementation of {@link ViewHolderDataList} tailored for data accessed via a {@link
 * ReviewViewAdapter} (A) (Model-View-Adapter pattern).
 * <p/>
 *
 * @param <T>: {@link GvData} type.GvDataList
 */
public class GvDataList<T extends GvData> extends ViewHolderDataList<T> implements GvData {
    public static final Parcelable.Creator<GvDataList> CREATOR = new Parcelable
            .Creator<GvDataList>() {
        public GvDataList createFromParcel(Parcel in) {
            return new GvDataList(in);
        }

        public GvDataList[] newArray(int size) {
            return new GvDataList[size];
        }
    };
    public final GvDataType mType;
    public       Class<T>   mDataClass;

    protected GvDataList(Class<T> dataClass, GvDataType mDataType) {
        mDataClass = dataClass;
        mType = mDataType;
    }

    //TODO make type safe
    public GvDataList(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mType = (GvDataType) in.readSerializable();
        T[] data = (T[]) in.readParcelableArray(mDataClass.getClassLoader());
        mData = new ArrayList<>(Arrays.asList(data));
    }

    public GvDataType getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mDataClass);
        dest.writeSerializable(mType);
        dest.writeParcelableArray((T[]) mData.toArray(), flags);
    }

    @Override
    public ViewHolder newViewHolder() {
        return new VhDataList();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof GvDataList)) return false;

        //TODO make type safe
        GvDataList<T> other = (GvDataList<T>) o;

        if (other.size() != size()) return false;

        for (int i = 0; i < size(); ++i) {
            T datum = getItem(i);
            T otherDatum = other.getItem(i);
            if (!datum.equals(otherDatum)) return false;
        }

        return true;
    }
}
