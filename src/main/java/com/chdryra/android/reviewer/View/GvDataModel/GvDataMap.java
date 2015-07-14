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
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 13/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMap<T1 extends GvData, T2 extends GvData> implements
        GvDataCollection<T1> {
    public static final Parcelable.Creator<GvDataMap> CREATOR = new Parcelable
            .Creator<GvDataMap>() {
        public GvDataMap createFromParcel(Parcel in) {
            return new GvDataMap(in);
        }

        public GvDataMap[] newArray(int size) {
            return new GvDataMap[size];
        }
    };

    private Map<T1, T2> mMap;
    private GvDataType<T1> mKeyType;
    private GvDataType<T2> mValueType;
    private GvReviewId mReviewId;
    private GvDataType mType;

    public GvDataMap(GvDataType<T1> keyType, GvDataType<T2> valueType, GvReviewId reviewId) {
        mMap = new LinkedHashMap<>();
        mKeyType = keyType;
        mValueType = valueType;
        mReviewId = reviewId;
        mType = GvTypeMaker.newType(this.getClass(), keyType);
    }

    //TODO make type safe
    public GvDataMap(Parcel in) {
        mMap = new LinkedHashMap<>();
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
        mKeyType = in.readParcelable(GvDataType.class.getClassLoader());
        mValueType = in.readParcelable(GvDataType.class.getClassLoader());
        Class<T1> keyClass = mKeyType.getDataClass();
        Class<T2> valueClass = mValueType.getDataClass();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            T1 key = in.readParcelable(keyClass.getClassLoader());
            T2 value = in.readParcelable(valueClass.getClassLoader());
            mMap.put(key, value);
        }
    }

    public void put(T1 key, T2 datum) {
        mMap.put(key, datum);
    }

    @Override
    public GvDataType getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return String.valueOf(size()) + " " + mKeyType.getDatumName() + ":" + mValueType
                .getDatumName() + " pairs";
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
    public T1 getItem(int position) {
        int i = 0;
        for (Map.Entry<T1, T2> entry : mMap.entrySet()) {
            if (position == i++) return entry.getKey();
        }

        return null;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhDataCollection();
    }

    @Override
    public boolean isValidForDisplay() {
        return size() > 0;
    }

    public void removeAll() {
        mMap.clear();
    }

    public GvDataList<T1> getKeyList() {
        Set<T1> keys = mMap.keySet();
        GvDataList<T1> keyList = FactoryGvData.newDataList(mKeyType, mReviewId);
        for(T1 key : keys) {
            keyList.add(key);
        }

        return keyList;
    }

    public T2 get(T1 key) {
        return mMap.get(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMap.size());
        dest.writeParcelable(mReviewId, flags);
        dest.writeParcelable(mKeyType, flags);
        dest.writeParcelable(mValueType, flags);
        for (Map.Entry<T1, T2> entry : mMap.entrySet()) {
            dest.writeParcelable(entry.getKey(), flags);
            dest.writeParcelable(entry.getValue(), flags);
        }
    }
}
