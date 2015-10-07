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
public class GvDataType2<T extends GvData> implements Parcelable {
    public static final Creator<GvDataType2> CREATOR = new Parcelable.Creator<GvDataType2>() {
        //Overridden
        public GvDataType2 createFromParcel(Parcel in) {
            return new GvDataType2(in);
        }

        public GvDataType2[] newArray(int size) {
            return new GvDataType2[size];
        }
    };

    private final Class<T> mDataClass;
    private final String mDatumName;
    private final String mDataName;

    //Constructors
    public GvDataType2(Class<T> dataClass, String datum) {
        this(dataClass, datum, datum + "s");
    }

    public GvDataType2(Class<T> dataClass, String datum, String data) {
        mDataClass = dataClass;
        mDatumName = datum;
        mDataName = data;
    }

    //TODO make typesafe
    public GvDataType2(Parcel in) {
        mDataClass = (Class<T>) in.readValue(Class.class.getClassLoader());
        mDatumName = in.readString();
        mDataName = in.readString();
    }

    //Static methods
    public static <T1 extends GvData, T2 extends GvDataCollection<T1>> GvDataType2<T2> compoundType
            (Class<T2> dataClass, GvDataType2<T1> elementType) {
        return new GvCompoundType<>(dataClass, elementType);
    }

    //public methods
    public String getDatumName() {
        return mDatumName;
    }

    public String getDataName() {
        return mDataName;
    }

    public Class<T> getDataClass() {
        return mDataClass;
    }

    public GvDataType2 getElementType() {
        return this;
    }

    public boolean isCompoundType() {
        return getElementType() != this;
    }

    //Overridden
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataType2)) return false;

        GvDataType2<?> that = (GvDataType2<?>) o;

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
            GvDataType2<T2> {
        public static final Creator<GvCompoundType> CREATOR = new Parcelable
                .Creator<GvCompoundType>() {
            public GvCompoundType createFromParcel(Parcel in) {
                return new GvCompoundType(in);
            }

            public GvCompoundType[] newArray(int size) {
                return new GvCompoundType[size];
            }
        };

        private final GvDataType2<T1> mElementType;

        //Constructors
        public GvCompoundType(Parcel in) {
            super(in);
            mElementType = in.readParcelable(GvDataType2.class.getClassLoader());
        }

        private GvCompoundType(Class<T2> dataClass, GvDataType2<T1> elementType) {
            super(dataClass, elementType.getDatumName(), elementType.getDataName());
            mElementType = elementType;
        }

        //Overridden
        @Override
        public GvDataType2 getElementType() {
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
