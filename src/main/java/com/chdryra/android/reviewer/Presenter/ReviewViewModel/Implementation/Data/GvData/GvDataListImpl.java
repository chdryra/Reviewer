/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.VhDataList;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhDataCollection;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class GvDataListImpl<T extends GvData> extends VhDataList<T> implements GvDataList<T>{
    private static final String NO_CTOR_ERR = "Constructor not found: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private final GvDataType<T> mType;
    private final GvReviewId mReviewId;

    //Constructors
    GvDataListImpl(GvReviewId reviewId, GvDataList<T> data) {
        this(data.getGvDataType(), reviewId);
        for (T datum : data) {
            add(copy(datum));
        }
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return null;
    }

    public GvDataListImpl(@NotNull GvDataType<T> type, GvReviewId reviewId) {
        mReviewId = reviewId;
        mType = type;
    }

    GvDataListImpl(GvDataList<T> data) {
        this(data.getGvReviewId(), data);
    }

    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }

    @Override
    public DataSize getDataSize() {
        return new GvSize(getGvReviewId(), mType, size());
    }

    @Override
    protected Comparator<? super T> getDefaultComparator() {
        return GvDataComparators.getDefaultComparator(mType);
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        int num = size();
        String dataString = num == 1 ? mType.getDatumName() : mType.getDataName();
        return String.valueOf(size()) + " " + dataString;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasElements() {
        return !isEmpty();
    }

    @Override
    public boolean isVerboseCollection() {
        return true;
    }

    @Override
    public GvDataList<T> toList() {
        return this;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhDataCollection();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public boolean hasData() {
        return !isEmpty();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return hasData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataListImpl)) return false;

        GvDataListImpl<? extends GvData> that = (GvDataListImpl<? extends GvData>) o;

        if (!mType.equals(that.mType)) return false;

        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null) {
            return false;
        }
        if (!mType.equals(that.mType)) return false;
        if (size() != that.size()) return false;

        for (int i = 0; i < size(); ++i) {
            //TODO make type safe
            if (!getItem(i).equals(that.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }

    private T copy(T datum) {
        //TODO make type safe
        Class<T> dataClass = (Class<T>) datum.getClass();
        try {
            Constructor<T> ctor = dataClass.getConstructor(datum.getClass());
            return ctor.newInstance(datum);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(NO_CTOR_ERR + dataClass.getName());
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOCATION_ERR + dataClass.getName());
        }
    }
}
