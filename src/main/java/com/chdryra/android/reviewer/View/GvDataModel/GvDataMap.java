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
public class GvDataMap<K extends GvData, V extends GvData> implements
        GvDataCollection<K> {
    public static final Parcelable.Creator<GvDataMap> CREATOR = new Parcelable
            .Creator<GvDataMap>() {
        public GvDataMap createFromParcel(Parcel in) {
            return new GvDataMap(in);
        }

        public GvDataMap[] newArray(int size) {
            return new GvDataMap[size];
        }
    };

    private Map<K, V> mMap;
    private GvDataType<K> mKeyType;
    private GvDataType<V> mValueType;
    private GvReviewId mReviewId;
    private GvDataType mType;

    public GvDataMap(GvDataType<K> keyType, GvDataType<V> valueType, GvReviewId reviewId) {
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
        Class<K> keyClass = mKeyType.getDataClass();
        Class<V> valueClass = mValueType.getDataClass();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            K key = in.readParcelable(keyClass.getClassLoader());
            V value = in.readParcelable(valueClass.getClassLoader());
            mMap.put(key, value);
        }
    }

    public void put(K key, V datum) {
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
    public GvDataList<K> toList() {
        return getKeyList();
    }

    @Override
    public int size() {
        return mMap.size();
    }

    @Override
    public void sort() {

    }

    @Override
    public K getItem(int position) {
        int i = 0;
        for (Map.Entry<K, V> entry : mMap.entrySet()) {
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

    public GvDataList<K> getKeyList() {
        Set<K> keys = mMap.keySet();
        GvDataList<K> keyList = FactoryGvData.newDataList(mKeyType, mReviewId);
        for (K key : keys) {
            keyList.add(key);
        }

        return keyList;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return mMap.entrySet();
    }

    public V get(K key) {
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
        for (Map.Entry<K, V> entry : mMap.entrySet()) {
            dest.writeParcelable(entry.getKey(), flags);
            dest.writeParcelable(entry.getValue(), flags);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataMap)) return false;

        GvDataMap<?, ?> gvDataMap = (GvDataMap<?, ?>) o;

        if (!mMap.equals(gvDataMap.mMap)) return false;
        if (!mKeyType.equals(gvDataMap.mKeyType)) return false;
        if (!mValueType.equals(gvDataMap.mValueType)) return false;
        if (mReviewId != null ? !mReviewId.equals(gvDataMap.mReviewId) : gvDataMap.mReviewId !=
                null)
            return false;
        return mType.equals(gvDataMap.mType);

    }

    @Override
    public int hashCode() {
        int result = mMap.hashCode();
        result = 31 * result + mKeyType.hashCode();
        result = 31 * result + mValueType.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + mType.hashCode();
        return result;
    }
}
