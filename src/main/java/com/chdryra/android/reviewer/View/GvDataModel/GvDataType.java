/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataType<T extends GvData> implements Parcelable {
    public static final Parcelable.Creator<GvDataType> CREATOR = new Parcelable
            .Creator<GvDataType>() {
        public GvDataType createFromParcel(Parcel in) {
            return new GvDataType(in);
        }

        public GvDataType[] newArray(int size) {
            return new GvDataType[size];
        }
    };

    private final Class<T> mDataClass;
    private final String mDatumName;
    private final String mDataName;

    public GvDataType(Class<T> dataClass, String datum) {
        this(dataClass, datum, datum + "s");
    }

    public GvDataType(Class<T> dataClass, String datum, String data) {
        mDataClass = dataClass;
        mDatumName = datum;
        mDataName = data;
    }

    //TODO make typesafe
    public GvDataType(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mDatumName = in.readString();
        mDataName = in.readString();
    }

    public static <T1 extends GvData, T2 extends GvDataCollection<T1>> GvDataType<T2> compoundType
            (Class<T2> dataClass, GvDataType<T1> elementType) {
        return new GvCompoundType<>(dataClass, elementType);
    }

    public String getDatumName() {
        return mDatumName;
    }

    public String getDataName() {
        return mDataName;
    }

    public Class<T> getDataClass() {
        return mDataClass;
    }

    public GvDataType getElementType() {
        return this;
    }

    public boolean isCompoundType() {
        return getElementType() != this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataType)) return false;

        GvDataType<?> that = (GvDataType<?>) o;

        if (!mDataClass.equals(that.mDataClass)) return false;
        if (!mDatumName.equals(that.mDatumName)) return false;
        return mDataName.equals(that.mDataName);

    }

    @Override
    public int hashCode() {
        int result = mDataClass.hashCode();
        result = 31 * result + mDatumName.hashCode();
        result = 31 * result + mDataName.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mDataClass);
        dest.writeString(mDatumName);
        dest.writeString(mDataName);
    }

    private static class GvCompoundType<T1 extends GvData, T2 extends GvDataCollection<T1>> extends
            GvDataType<T2> {
        public static final Parcelable.Creator<GvCompoundType> CREATOR = new Parcelable
                .Creator<GvCompoundType>() {
            public GvCompoundType createFromParcel(Parcel in) {
                return new GvCompoundType(in);
            }

            public GvCompoundType[] newArray(int size) {
                return new GvCompoundType[size];
            }
        };

        private final GvDataType<T1> mElementType;

        private GvCompoundType(Class<T2> dataClass, GvDataType<T1> elementType) {
            super(dataClass, elementType.getDatumName(), elementType.getDataName());
            mElementType = elementType;
        }

        public GvCompoundType(Parcel in) {
            super(in);
            mElementType = in.readParcelable(GvDataType.class.getClassLoader());
        }

        @Override
        public GvDataType getElementType() {
            return mElementType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvCompoundType)) return false;
            if (!super.equals(o)) return false;

            GvCompoundType<?, ?> that = (GvCompoundType<?, ?>) o;

            return mElementType.equals(that.mElementType);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + mElementType.hashCode();
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(mElementType, flags);
        }
    }
}
