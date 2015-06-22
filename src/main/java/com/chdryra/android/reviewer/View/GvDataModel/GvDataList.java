/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The View layer (V) data equivalent of the Model layer (M) data {@link com.chdryra.android
 * .reviewer.Model.MdDataList}.
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

    private final GvDataType mType;
    private final Class<T>   mDataClass;
    private       GvReviewId mReviewId;

    //Copy constructor
    public GvDataList(GvReviewId reviewId, GvDataList<T> data) {
        this(reviewId, data.mDataClass, data.getGvDataType());
        for (T datum : data) {
            add(FactoryGvData.copy(datum, mDataClass));
        }
    }

    public GvDataList(GvReviewId reviewId, Class<T> dataClass, GvDataType mDataType) {
        mDataClass = dataClass;
        mType = mDataType;
        mReviewId = reviewId;
    }

    public GvDataList(GvDataList<T> data) {
        this(data.getReviewId(), data);
    }

    //TODO make type safe
    public GvDataList(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mType = (GvDataType) in.readSerializable();
        T[] data = (T[]) in.readParcelableArray(mDataClass.getClassLoader());
        mData = new ArrayList<>(Arrays.asList(data));
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    public Class<T> getDataClass() {
        return mDataClass;
    }

    @Override
    public GvDataType getGvDataType() {
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
    public void add(T item) {
        if (item.getGvDataType() == mType) {
            super.add(item);
        } else {
            String wrong = item.getGvDataType().getDatumName();
            String right = mType.getDatumName();
            String message = "Type mismatch! Item is: " + wrong + ", should be: " + right;
            throw new IllegalArgumentException(message);
        }
    }

    public boolean contains(GvData datum) {
        try {
            return super.contains(mDataClass.cast(datum));
        } catch (ClassCastException e) {
            return false;
        }
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
        dest.writeParcelable(mReviewId, flags);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhDataList();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataList)) return false;

        GvDataList that = (GvDataList) o;

        if (!mDataClass.equals(that.mDataClass)) return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null) {
            return false;
        }
        if (!mType.equals(that.mType)) return false;
        if (size() != that.size()) return false;

        for (int i = 0; i < size(); ++i) {
            if (!getItem(i).equals(that.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + mDataClass.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        for (T datum : this) {
            result = 31 * result + datum.hashCode();
        }

        return result;
    }
}
