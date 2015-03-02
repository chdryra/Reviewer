/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;

import java.io.Serializable;

/**
 * The View layer (V) data equivalent of the Model layer (M) data {@link MdDataList}.
 * Implementation of {@link ViewHolderDataList} tailored for data accessed via a {@link
 * ReviewViewAdapter} (A) (Model-View-Adapter pattern).
 * <p/>
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
public abstract class GvDataList<T extends GvDataList.GvData> extends ViewHolderDataList<T> {
    public final GvDataType TYPE;

    public interface GvData extends ViewHolderData, Parcelable {
    }

    protected GvDataList(GvDataType dataName) {
        TYPE = dataName;
    }

    public GvDataType getGvDataType() {
        return TYPE;
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

    public static class GvDataType implements Serializable {
        private final String mDatumName;
        private final String mDataName;

        protected GvDataType(String datum) {
            mDatumName = datum;
            mDataName = datum + "s";
        }

        protected GvDataType(String datum, String data) {
            mDatumName = datum;
            mDataName = data;
        }

        public String getDatumName() {
            return mDatumName;
        }

        public String getDataName() {
            return mDataName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvDataType)) return false;

            GvDataType that = (GvDataType) o;

            if (!mDataName.equals(that.mDataName)) return false;
            if (!mDatumName.equals(that.mDatumName)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = mDatumName.hashCode();
            result = 31 * result + mDataName.hashCode();
            return result;
        }
    }
}
