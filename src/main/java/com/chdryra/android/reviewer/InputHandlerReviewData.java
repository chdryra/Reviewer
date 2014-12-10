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

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 * data set to check whether valid for addition, deletion or editing. Also takes care of
 * packing and unpacking data into Bundles/Intents between different fragments.
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
class InputHandlerReviewData<T extends GvDataList.GvData> {
    private static final String DATUM_CURRENT = "com.chdryra.android.reviewer.data_current";
    private static final String DATUM_NEW     = "com.chdryra.android.reviewer.data_new";
    private GvDataList<T>     mData;
    private GvDataList.GvType mDataType;

    enum CurrentNewDatum {
        CURRENT(DATUM_CURRENT),
        NEW(DATUM_NEW);

        private final String mTag;

        CurrentNewDatum(String tag) {
            mTag = tag;
        }

        private String getPackingTag() {
            return mTag;
        }
    }

    InputHandlerReviewData(GvDataList.GvType dataType) {
        mDataType = dataType;
    }

    GvDataList.GvType getGVType() {
        return mDataType;
    }

    GvDataList<T> getData() {
        return mData;
    }

    void setData(GvDataList<T> data) {
        mData = data;
        mDataType = data.getGvType();
    }

    void pack(CurrentNewDatum currentNew, T item, Bundle args) {
        if (item != null && !item.isValidForDisplay()) item = null;
        args.putParcelable(currentNew.getPackingTag(), item);
    }

    void pack(CurrentNewDatum currentNew, T item, Intent data) {
        if (item != null && !item.isValidForDisplay()) item = null;
        data.putExtra(currentNew.getPackingTag(), item);
    }

    T unpack(CurrentNewDatum currentNew, Bundle args) {
        return args.getParcelable(currentNew.getPackingTag());
    }

    T unpack(CurrentNewDatum currentNew, Intent data) {
        return data.getParcelableExtra(currentNew.getPackingTag());
    }

    boolean add(T newDatum, Context context) {
        if (passesAddConstraint(newDatum, context)) {
            mData.add(newDatum);
            return true;
        }

        return false;
    }

    void replace(T oldDatum, T newDatum, Context context) {
        if (passesReplaceConstraint(oldDatum, newDatum, context)) {
            mData.remove(oldDatum);
            mData.add(newDatum);
        }
    }

    void delete(T data) {
        if (mData != null) mData.remove(data);
    }

    boolean contains(T datum, Context context) {
        if (mData != null && mData.contains(datum)) {
            if (context != null) makeToastHasItem(context);
            return true;
        }

        return false;
    }

    void makeToastHasItem(Context context) {
        String toast = context.getResources().getString(R.string.toast_has) + " " +
                getGVType().getDatumString();
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }

    boolean isNewAndValid(T datum, Context context) {
        return isValid(datum) && !contains(datum, context);
    }

    boolean passesAddConstraint(T datum, Context context) {
        return isNewAndValid(datum, context);
    }

    boolean passesReplaceConstraint(T oldDatum, T newDatum, Context context) {
        return isValid(oldDatum) && !oldDatum.equals(newDatum) && isNewAndValid(newDatum, context);
    }
}
