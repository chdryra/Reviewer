/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .DataObservableDefault;

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public class DataBuilderImpl<T extends GvData> extends DataObservableDefault implements
        DataBuilder<T> {
    private final AddConstraint<T> mAddConstraint;
    private final ReplaceConstraint<T> mReplaceConstraint;
    private final FactoryGvData mCopier;

    private GvDataList<T> mOriginalData;
    private GvDataList<T> mData;

    public DataBuilderImpl(GvDataList<T> data, FactoryGvData copier) {
        this(data, copier, new AddConstraintDefault<T>());
    }

    public DataBuilderImpl(GvDataList<T> data,
                           FactoryGvData copier,
                           AddConstraint<T> addConstraint) {
        this(data, copier, addConstraint, new ReplaceConstraintDefault<T>());
    }

    public DataBuilderImpl(GvDataList<T> data,
                           FactoryGvData copier,
                           AddConstraint<T> addConstraint,
                           ReplaceConstraint<T> replaceConstraint) {
        mAddConstraint = addConstraint;
        mReplaceConstraint = replaceConstraint;
        mOriginalData = data;
        mCopier = copier;
        resetData();
    }

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
            if (res == ConstraintResult.PASSED) {
                mData.add(newDatum);
                notifyDataObservers();
            }
        } else {
            res = ConstraintResult.INVALID_DATUM;
        }

        return res;
    }

    @Override
    public ConstraintResult replace(T oldDatum, T newDatum) {
        ConstraintResult res;
        if (oldDatum.equals(newDatum)) {
            res = ConstraintResult.OLD_EQUALS_NEW;
        } else if (!isValid(oldDatum) || !isValid(newDatum)) {
            res = ConstraintResult.INVALID_DATUM;
        } else {
            res = mReplaceConstraint.passes(mData, oldDatum, newDatum);
            if (res == ConstraintResult.PASSED) {
                mData.remove(oldDatum);
                mData.add(newDatum);
                notifyDataObservers();
            }
        }

        return res;
    }

    @Override
    public boolean delete(T data) {
        boolean remove = mData.remove(data);
        notifyDataObservers();

        return remove;
    }

    @Override
    public void deleteAll() {
        mData.clear();
        notifyDataObservers();
    }

    @Override
    public void resetData() {
        mData = mCopier.copy(mOriginalData);
        notifyDataObservers();
    }

    @Override
    public void commitData() {
        mOriginalData = mData;
        resetData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBuilderImpl)) return false;

        DataBuilderImpl<?> that = (DataBuilderImpl<?>) o;

        if (!mAddConstraint.equals(that.mAddConstraint)) return false;
        if (!mReplaceConstraint.equals(that.mReplaceConstraint)) return false;
        if (!mCopier.equals(that.mCopier)) return false;
        if (!mOriginalData.equals(that.mOriginalData)) return false;
        return mData.equals(that.mData);

    }

    @Override
    public int hashCode() {
        int result = mAddConstraint.hashCode();
        result = 31 * result + mReplaceConstraint.hashCode();
        result = 31 * result + mCopier.hashCode();
        result = 31 * result + mOriginalData.hashCode();
        result = 31 * result + mData.hashCode();
        return result;
    }

    GvDataList<T> getOriginalData() {
        return mOriginalData;
    }

    private boolean isValid(T datum) {
        return datum != null && datum.isValidForDisplay();
    }
}
