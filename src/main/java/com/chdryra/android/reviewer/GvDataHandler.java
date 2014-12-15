/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
class GvDataHandler<T extends GvDataList.GvData> {
    private GvDataList<T>    mData;
    private AddConstraint<T> mAddConstraint;

    public GvDataHandler(GvDataList<T> data) {
        mData = data;
        mAddConstraint = new AddConstraint<>();
    }

    public GvDataHandler(GvDataList<T> data, AddConstraint<T> addConstraint) {
        mData = data;
        mAddConstraint = addConstraint;
    }

    public boolean add(T newDatum, Context context) {
        if (isValid(newDatum)) {
            if (mAddConstraint.passes(mData, newDatum)) {
                mData.add(newDatum);
                return true;
            } else if (context != null) {
                makeToastHasItem(context);
            }
        }

        return false;
    }

    public void replace(T oldDatum, T newDatum, Context context) {
        if (isValid(oldDatum) && isValid(newDatum)) {
            if (!oldDatum.equals(newDatum) && !mData.contains(newDatum)) {
                mData.remove(oldDatum);
                mData.add(newDatum);
            } else if (context != null) {
                makeToastHasItem(context);
            }
        }
    }

    public void delete(T data) {
        mData.remove(data);
    }

    private void makeToastHasItem(Context context) {
        String toast = context.getResources().getString(R.string.toast_has) + " " + mData
                .getGvType().getDatumString();
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    private boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }

    public static class AddConstraint<G extends GvDataList.GvData> {
        public boolean passes(GvDataList<G> data, G datum) {
            return !data.contains(datum);
        }
    }
}
