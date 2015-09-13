/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTypeMaker {
    private static GvTypeMaker           sMaker;
    private        ArrayList<GvDataType> mTypes;

    private GvTypeMaker() {
        mTypes = new ArrayList<>();
    }

    private static GvTypeMaker get() {
        if (sMaker == null) sMaker = new GvTypeMaker();
        return sMaker;
    }

    public static <T1 extends GvData, T2 extends GvDataCollection> GvDataType<T2>
    newType(Class<T2> dataClass, GvDataType<T1> elementType) {
        return new GvCompoundType<>(dataClass, elementType);
    }

    private static class GvCompoundType<T1 extends GvData, T2 extends GvDataCollection<T1>> extends
            GvDataType<T2> {
        public static final Creator<GvCompoundType> CREATOR = new Parcelable
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
