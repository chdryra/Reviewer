/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.content.Context;
import android.widget.Toast;

import com.chdryra.android.reviewer.R;


/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public class GvDataHandler<T extends GvData> {
    private final GvDataList<T>        mData;
    private final AddConstraint<T>     mAddConstraint;
    private final ReplaceConstraint<T> mReplaceConstraint;

    public GvDataHandler(GvDataList<T> data) {
        this(data, new AddConstraint<T>());
    }

    public GvDataHandler(GvDataList<T> data, AddConstraint<T> addConstraint) {
        this(data, addConstraint, new ReplaceConstraint<T>());
    }

    public GvDataHandler(GvDataList<T> data, AddConstraint<T> addConstraint,
            ReplaceConstraint<T> relaceConstraint) {
        mData = data;
        mAddConstraint = addConstraint;
        mReplaceConstraint = relaceConstraint;
    }

    public GvDataType<T> getGvDataType() {
        //TODO make type safe
        return mData.getGvDataType();
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
        if (!oldDatum.equals(newDatum) && isValid(oldDatum) && isValid(newDatum)) {
            if (mReplaceConstraint.passes(mData, oldDatum, newDatum)) {
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

    public void deleteAll() {
        mData.removeAll();
        ;
    }

    public GvDataList<T> getData() {
        return mData;
    }

    private void makeToastHasItem(Context context) {
        String toast = context.getResources().getString(R.string.toast_has) + " " + mData
                .getGvDataType().getDatumName();
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    private boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }

    public static class AddConstraint<G extends GvData> {
        public boolean passes(GvDataList<G> data, G datum) {
            return !data.contains(datum);
        }
    }

    public static class ReplaceConstraint<G extends GvData> {
        public boolean passes(GvDataList<G> data, G oldDatum, G newDatum) {
            return !data.contains(newDatum);
        }
    }
}
