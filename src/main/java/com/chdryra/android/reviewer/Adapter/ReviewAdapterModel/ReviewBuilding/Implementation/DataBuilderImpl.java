/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilder;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

import java.util.ArrayList;

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public class DataBuilderImpl<T extends GvData> implements DataBuilder<T> {
    private final AddConstraint<T> mAddConstraint;
    private final ReplaceConstraint<T> mReplaceConstraint;
    private final ArrayList<DataBuilderObserver> mObservers;
    
    private GvDataList<T> mResetData;
    private GvDataList<T> mData;

    //Constructors
    public DataBuilderImpl(GvDataList<T> data) {
        this(data, new AddConstraintImpl<T>());
    }

    public DataBuilderImpl(GvDataList<T> data,
                           AddConstraint<T> addConstraint) {
        this(data, addConstraint, new ReplaceConstraintImpl<T>());
    }

    public DataBuilderImpl(GvDataList<T> data,
                           AddConstraint<T> addConstraint,
                           ReplaceConstraint<T> replaceConstraint) {
        mAddConstraint = addConstraint;
        mReplaceConstraint = replaceConstraint;
        mObservers = new ArrayList<>();
        mResetData = data;
        resetData();
    }

    //public methods
    @Override
    public GvDataType<T> getGvDataType() {
        return mData.getGvDataType();
    }

    @Override
    public GvDataList<T> getData() {
        return mData;
    }

    @Override
    public ConstraintResult add(T newDatum) {
        ConstraintResult res;
        if (isValid(newDatum)) {
            res = mAddConstraint.passes(mData, newDatum);
            if (res == ConstraintResult.PASSED) ((GvDataCollection<T>)mData).add(newDatum);
        } else {
            res = ConstraintResult.INVALID_DATUM;
        }

        return res;
    }

    @Override
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
                ((GvDataCollection<T>)mData).add(newDatum);
            }
        }

        return res;
    }

    @Override
    public void delete(T data) {
        mData.remove(data);
    }

    @Override
    public void deleteAll() {
        mData.clear();
    }

    @Override
    public void resetData() {
        mData = new GvDataListImpl<>(mResetData);
    }

    @Override
    public void registerObserver(DataBuilderObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void publishData() {
        for(DataBuilderObserver observer : mObservers) {
            observer.onDataPublished(this);
        }
    }

    private boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }

    //Classes
    public static class AddConstraintImpl<G extends GvData> implements AddConstraint<G>{
        @Override
        public ConstraintResult passes(GvDataList<G> data, G datum) {
            if(data == null) {
                return ConstraintResult.NULL_LIST;
            } else {
                return !data.contains(datum) ? ConstraintResult.PASSED : ConstraintResult.HAS_DATUM;
            }
        }
    }

    public static class ReplaceConstraintImpl<G extends GvData> implements ReplaceConstraint<G>{
        @Override
        public ConstraintResult passes(GvDataList<G> data, G oldDatum, G newDatum) {
            if(data == null) {
                return ConstraintResult.NULL_LIST;
            } else {
                return !data.contains(newDatum) ? ConstraintResult.PASSED : ConstraintResult.HAS_DATUM;

            }
        }
    }
}
