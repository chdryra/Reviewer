/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.GVData;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
abstract class InputHandlerReviewData<T extends GVData> {
    private static final String DATUM_CURRENT = "com.chdryra.android.reviewer.data_current";
    private static final String DATUM_NEW     = "com.chdryra.android.reviewer.data_new";
    protected GVReviewDataList<T>     mData;
    private   GVReviewDataList.GVType mDataType;

    InputHandlerReviewData(GVReviewDataList.GVType dataType) {
        mDataType = dataType;
    }

    InputHandlerReviewData(GVReviewDataList<T> data) {
        mDataType = data.getGVType();
        mData = data;
    }

    protected static String getCurrentDataTag() {
        return DATUM_CURRENT;
    }

    protected static String getNewDataTag() {
        return DATUM_NEW;
    }

    ;

    GVReviewDataList.GVType getGVType() {
        return mDataType;
    }

    void setData(GVReviewDataList<T> data) {
        mData = data;
    }

    abstract void pack(CurrentNewDatum currentNew, T item, Bundle args);

    abstract void pack(CurrentNewDatum currentNew, T item, Intent data);

    abstract T unpack(CurrentNewDatum currentNew, Bundle args);

    abstract T unpack(CurrentNewDatum currentNew, Intent data);

    abstract void add(Intent data, Context context);

    abstract void replace(Intent data, Context context);

    abstract void delete(Intent data);

    enum CurrentNewDatum {
        CURRENT(DATUM_CURRENT),
        NEW(DATUM_NEW);

        private String mTag;

        CurrentNewDatum(String tag) {
            mTag = tag;
        }

        String getPackingTag() {
            return mTag;
        }
    }
}
