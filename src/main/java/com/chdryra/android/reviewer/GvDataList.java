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
import java.util.Iterator;

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

    private final GvDataType mType;
    private final Class<T>   mDataClass;
    private       GvReviewId mReviewId;

    public GvDataList(Class<T> dataClass, GvDataType mDataType) {
        mDataClass = dataClass;
        mType = mDataType;
    }

    //TODO come up with a more robust way of doing this.
    public GvDataList(GvReviewId reviewId, GvDataList<T> data) {
        mDataClass = data.mDataClass;
        mType = data.getGvDataType();
        mReviewId = reviewId;
        for (T datum : data) {
            if (reviewId.equals(datum.getHoldingReviewId())) super.add(datum);
        }
    }

    //TODO make type safe
    public GvDataList(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mType = (GvDataType) in.readSerializable();
        T[] data = (T[]) in.readParcelableArray(mDataClass.getClassLoader());
        mData = new ArrayList<>(Arrays.asList(data));
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

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
    public boolean hasHoldingReview() {
        return mReviewId != null;
    }

    @Override
    public GvReviewId getHoldingReviewId() {
        return mReviewId;
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
    public ViewHolder newViewHolder() {
        return new VhDataList();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public void add(T item) {
        if (isModifiable()) super.add(item);
    }

    @Override
    public void remove(T item) {
        if (isModifiable()) super.remove(item);
    }

    @Override
    public void removeAll() {
        if (isModifiable()) super.removeAll();
    }

    @Override
    public void add(Iterable<T> list) {
        if (isModifiable()) super.add(list);
    }

    @Override
    public Iterator<T> iterator() {
        return new GvDataListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataList)) return false;

        GvDataList that = (GvDataList) o;

        if (!mDataClass.equals(that.mDataClass)) return false;
        if (!mReviewId.equals(that.mReviewId)) return false;
        if (!mType.equals(that.mType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + mDataClass.hashCode();
        result = 31 * result + mReviewId.hashCode();
        return result;
    }

    protected boolean isModifiable() {
        return !hasHoldingReview();
    }

    public class GvDataListIterator extends SortableListIterator {
        @Override
        public void remove() {
            if (isModifiable()) super.remove();
        }
    }
}
