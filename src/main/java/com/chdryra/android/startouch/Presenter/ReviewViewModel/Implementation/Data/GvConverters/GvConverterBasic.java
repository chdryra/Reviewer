/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterBasic<T1, T2 extends GvData, T3 extends GvDataList<T2>>
        implements DataConverter<T1, T2, T3> {
    private static final String NO_CTOR_ERR = "Constructor not found: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";
    private static final String ILLEGAL_ARGUMENT_ERR = "Need to override newList if listClass not passed";

    private GvDataType<T2> mDataType;
    private Class<T3> mListClass;

    GvConverterBasic(Class<T3> listClass) {
        mListClass = listClass;
        setDataType();
    }

    GvConverterBasic(GvDataType<T2> dataType) {
        mDataType = dataType;
    }

    public GvDataType<T2> getOutputType() {
        return mDataType;
    }

    @Nullable
    GvReviewId newId(@Nullable ReviewId reviewId) {
        return reviewId != null ? new GvReviewId(reviewId) : null;
    }

    @Override
    public abstract T2 convert(T1 datum, @Nullable ReviewId reviewId);

    @Override
    public T2 convert(T1 datum) {
        return convert(datum, null);
    }

    @Override
    public T3 convert(Iterable<? extends T1> data, @Nullable ReviewId reviewId) {
        T3 list = newList(reviewId);
        for(T1 datum : data) {
            list.add(convert(datum, reviewId));
        }

        return list;
    }

    @Override
    public T3 convert(IdableList<? extends T1> data) {
        T3 list = newList(data.getReviewId());
        for(T1 datum : data) {
            list.add(convert(datum));
        }

        return list;
    }

    private void setDataType() {
        try {
            mDataType = mListClass.newInstance().getGvDataType();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + mListClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + mListClass.getName(), e);
        }
    }

    T3 newList(@Nullable ReviewId reviewId) {
        GvReviewId id = reviewId != null ? new GvReviewId(reviewId) : new GvReviewId();
        if(mListClass != null) {
            try {
                Constructor<T3> ctor = mListClass.getConstructor(GvReviewId.class);
                return ctor.newInstance(id);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(NO_CTOR_ERR + mListClass.getName(), e);
            } catch (InstantiationException e) {
                throw new RuntimeException(INSTANTIATION_ERR + mListClass.getName(), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(ILLEGAL_ACCESS_ERR + mListClass.getName(), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(INVOCATION_ERR + mListClass.getName());
            }
        } else {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_ERR);
        }
    }
}
