/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 March, 2015
 */

package com.chdryra.android.reviewer.View;

import java.io.Serializable;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataType implements Serializable {
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
