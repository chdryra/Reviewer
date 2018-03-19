/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataType<T extends GvData> implements Parcelable {
    public static final Parcelable.Creator<GvDataType> CREATOR = new Parcelable
            .Creator<GvDataType>() {
        @Override
        public GvDataType createFromParcel(Parcel in) {
            return new GvDataType(in);
        }

        @Override
        public GvDataType[] newArray(int size) {
            return new GvDataType[size];
        }
    };

    private final Class<T> mDataClass;
    private final String mDatumName;
    private final String mDataName;

    //Constructors
    public GvDataType(@NotNull Class<T> dataClass, String datum) {
        this(dataClass, datum, datum + "s");
    }

    public GvDataType(@NotNull Class<T> dataClass, String datum, String data) {
        mDataClass = dataClass;
        mDatumName = datum;
        mDataName = data;
    }

    public GvDataType(@NotNull Class<T> dataClass, GvDataType<?> forNaming) {
        mDataClass = dataClass;
        mDatumName = forNaming.getDatumName();
        mDataName = forNaming.getDataName();
    }

    public GvDataType(Parcel in) {
        this((GvDataType<T>) loadFromParcel(in));
    }

    private GvDataType(GvDataType<T> dataType) {
        mDataClass = dataType.getDataClass();
        mDatumName = dataType.getDatumName();
        mDataName = dataType.getDataName();
    }

    public static GvDataType<? extends GvData> loadFromParcel(Parcel in) {
        try {
            Class<? extends GvData> aClass = (Class<? extends GvData>) Class.forName(in
                    .readString());
            return new GvDataType<>(aClass, in.readString(), in.readString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToParcel(GvDataType<? extends GvData> dataType, Parcel parcel) {
        parcel.writeString(dataType.getDataClass().getName());
        parcel.writeString(dataType.getDatumName());
        parcel.writeString(dataType.getDataName());
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

    @Override
    public String toString() {
        return mDataClass.getCanonicalName();
    }

    //Overridden
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
        writeToParcel(this, dest);
    }
}
