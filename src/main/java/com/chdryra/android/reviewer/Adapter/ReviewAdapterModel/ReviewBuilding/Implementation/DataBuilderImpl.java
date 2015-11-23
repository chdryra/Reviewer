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

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces
        .DataConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilder;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilder;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Handles user inputs of review data. Checks validity of data and compares user input to current
 *
 * @param <T>: {@link GvData} type.
 */
public class DataBuilderImpl<T extends GvData> implements DataBuilder<T> {
    private final ReviewBuilder mParentBuilder;
    private final DataConverter<? super T, T, ? extends GvDataList<T>> mCopier;
    private final AddConstraint<T> mAddConstraint;
    private final ReplaceConstraint<T> mReplaceConstraint;
    private final GvDataType<T> mDataType;
    private GvDataList<T> mData;

    //Constructors
    public DataBuilderImpl(GvDataType<T> dataType,
                           ReviewBuilder parentBuilder,
                           DataConverter<? super T, T, ? extends GvDataList<T>> copier) {
        this(dataType, parentBuilder, copier, new AddConstraintImpl<T>());
    }

    public DataBuilderImpl(GvDataType<T> dataType,
                           ReviewBuilder parentBuilder,
                           DataConverter<? super T, T, ? extends GvDataList<T>> copier,
                           AddConstraint<T> addConstraint) {
        this(dataType, parentBuilder, copier, addConstraint, new ReplaceConstraintImpl<T>());
    }

    public DataBuilderImpl(GvDataType<T> dataType,
                           ReviewBuilder parentBuilder,
                           DataConverter<? super T, T, ? extends GvDataList<T>> copier,
                           AddConstraint<T> addConstraint,
                           ReplaceConstraint<T> replaceConstraint) {
        mDataType = dataType;
        mParentBuilder = parentBuilder;
        mCopier = copier;
        mAddConstraint = addConstraint;
        mReplaceConstraint = replaceConstraint;
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
            if (res == ConstraintResult.PASSED) mData.add(newDatum);
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
                mData.add(newDatum);
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
        mData.removeAll();
    }

    @Override
    public void setData() {
        mParentBuilder.setData(this);
    }

    @Override
    public void resetData() {
        mData = mCopier.convert(mParentBuilder.getData(mDataType));
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
