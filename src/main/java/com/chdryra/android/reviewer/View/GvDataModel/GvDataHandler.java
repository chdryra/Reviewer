/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

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
    private final GvDataList<T> mData;
    private final AddConstraint<T> mAddConstraint;
    private final ReplaceConstraint<T> mReplaceConstraint;

    //Constructors
    public GvDataHandler(GvDataList<T> data) {
        this(data, new AddConstraint<T>());
    }

    public GvDataHandler(GvDataList<T> data, AddConstraint<T> addConstraint) {
        this(data, addConstraint, new ReplaceConstraint<T>());
    }

    public GvDataHandler(GvDataList<T> data, AddConstraint<T> addConstraint,
                         ReplaceConstraint<T> replaceConstraint) {
        mData = data;
        mAddConstraint = addConstraint;
        mReplaceConstraint = replaceConstraint;
    }

    //public methods
    public GvDataType<T> getGvDataType() {
        return mData.getGvDataType();
    }

    public GvDataList<T> getData() {
        return mData;
    }

    public ConstraintResult add(T newDatum) {
        ConstraintResult res;
        if (isValid(newDatum)) {
            res = mAddConstraint.passes(mData, newDatum);
            if (res == ConstraintResult.PASSED) mData.add(newDatum);
        } else {
            res = ConstraintResult.INVALID_DATUM;
        }

        return res;
    }

    public ConstraintResult replace(T oldDatum, T newDatum) {
        ConstraintResult res;
        if(oldDatum.equals(newDatum)) {
            res = ConstraintResult.OLD_EQUALS_NEW;
        } else if(!isValid(oldDatum) || !isValid(newDatum)) {
            res = ConstraintResult.INVALID_DATUM;
        } else {
            res = mReplaceConstraint.passes(mData, oldDatum, newDatum);
            if (res == ConstraintResult.PASSED) {
                mData.remove(oldDatum);
                mData.add(newDatum);
            }
        }

        return res;
    }

    public void delete(T data) {
        mData.remove(data);
    }

    public void deleteAll() {
        mData.removeAll();
    }

    private boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }

    //Classes
    public static class AddConstraint<G extends GvData> {
        public ConstraintResult passes(GvDataList<G> data, G datum) {
            if(data == null) {
                return ConstraintResult.NULL_LIST;
            } else {
                return !data.contains(datum) ? ConstraintResult.PASSED : ConstraintResult.HAS_DATUM;
            }
        }
    }

    public static class ReplaceConstraint<G extends GvData> {
        public ConstraintResult passes(GvDataList<G> data, G oldDatum, G newDatum) {
            if(data == null) {
                return ConstraintResult.NULL_LIST;
            } else {
                return !data.contains(newDatum) ? ConstraintResult.PASSED : ConstraintResult.HAS_DATUM;

            }
        }
    }

    public enum ConstraintResult {
        PASSED,
        NULL_LIST,
        HAS_DATUM,
        INVALID_DATUM,
        OLD_EQUALS_NEW
    }
}
