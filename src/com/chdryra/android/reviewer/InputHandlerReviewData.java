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
import android.widget.Toast;


/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class InputHandlerReviewData<T extends GVReviewDataList.GVReviewData> {
    private static final String DATUM_CURRENT = "com.chdryra.android.reviewer.data_current";
    private static final String DATUM_NEW     = "com.chdryra.android.reviewer.data_new";
    protected GVReviewDataList<T>     mData;
    private   GVReviewDataList.GVType mDataType;

    enum CurrentNewDatum {
        CURRENT(DATUM_CURRENT),
        NEW(DATUM_NEW);

        private String mTag;

        CurrentNewDatum(String tag) {
            mTag = tag;
        }

        private String getPackingTag() {
            return mTag;
        }
    }

    InputHandlerReviewData(GVReviewDataList.GVType dataType) {
        mDataType = dataType;
    }

    InputHandlerReviewData(GVReviewDataList<T> data) {
        setData(data);
    }

    GVReviewDataList.GVType getGVType() {
        return mDataType;
    }

    GVReviewDataList<T> getData() {
        return mData;
    }

    void setData(GVReviewDataList<T> data) {
        mData = data;
        mDataType = data.getGVType();
    }

    void pack(CurrentNewDatum currentNew, T item, Bundle args) {
        args.putParcelable(currentNew.getPackingTag(), item);
    }

    void pack(CurrentNewDatum currentNew, T item, Intent data) {
        data.putExtra(currentNew.getPackingTag(), item);
    }

    T unpack(CurrentNewDatum currentNew, Bundle args) {
        return args.getParcelable(currentNew.getPackingTag());
    }

    T unpack(CurrentNewDatum currentNew, Intent data) {
        return data.getParcelableExtra(currentNew.getPackingTag());
    }

    void add(Intent data, Context context) {
        T newDatum = unpack(CurrentNewDatum.NEW, data);
        if (isNewAndValid(newDatum, context)) mData.add(newDatum);
    }

    void replace(Intent data, Context context) {
        T oldDatum = unpack(CurrentNewDatum.CURRENT, data);
        T newDatum = unpack(CurrentNewDatum.NEW, data);
        if (isNewAndValid(newDatum, context)) {
            mData.remove(oldDatum);
            mData.add(newDatum);
        }
    }

    void delete(Intent data) {
        if (mData != null) mData.remove(unpack(CurrentNewDatum.CURRENT, data));
    }

    boolean contains(T datum, Context context) {
        if (mData != null && mData.contains(datum)) {
            if (context != null) {
                String toast = context.getResources().getString(R.string.toast_has) + " " +
                        getGVType().getDatumString();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return false;
    }

    boolean isNewAndValid(T datum, Context context) {
        return datum.isValidForDisplay() && !contains(datum, context);
    }
}
