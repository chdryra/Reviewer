/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.Viewholder.VhDataList;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderDataList;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataCollection;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The View layer (V) data equivalent of the Model layer (M) data {@link com.chdryra.android
 * .reviewer.Model.MdDataList}.
 * Implementation of {@link ViewHolderDataList} tailored for data accessed via a {@link
 * ReviewViewAdapter} (A) (Model-View-Adapter pattern).
 * <p/>
 *
 * @param <T>: {@link GvData} type.GvDataList
 */
public class GvDataListImpl<T extends GvData> extends VhDataList<T> implements GvDataList<T> {
    public static final Parcelable.Creator<GvDataList> CREATOR = new Parcelable
            .Creator<GvDataList>() {
        @Override
        public GvDataList createFromParcel(Parcel in) {
            return new GvDataListImpl(in);
        }

        @Override
        public GvDataList[] newArray(int size) {
            return new GvDataList[size];
        }
    };
    private static final String NO_CTOR_ERR = "Constructor not found: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private final GvDataType<T> mType;
    private GvReviewId mReviewId;

    //Constructors
    public GvDataListImpl(GvReviewId reviewId, GvDataList<T> data) {
        this(data.getGvDataType(), reviewId);
        for (T datum : data) {
            add(copy(datum));
        }
    }

    public GvDataListImpl(@NotNull GvDataType<T> mDataType, GvReviewId reviewId) {
        mReviewId = reviewId;
        mType = mDataType;
    }

    //Copy constructor
    public GvDataListImpl(GvDataList<T> data) {
        this(data.getGvReviewId(), data);
    }

    public GvDataListImpl(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        //TODO make type safe
        T[] data = (T[]) in.readParcelableArray(mType.getDataClass().getClassLoader());
        mData = new ArrayList<>(Arrays.asList(data));
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }


    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }


    @Override
    protected Comparator<? super T> getDefaultComparator() {
        return GvDataComparators.getDefaultComparator(mType);
    }

    //Overridden
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mType, flags);
        //TODO make type safe
        dest.writeParcelableArray((T[]) mData.toArray(), flags);
        dest.writeParcelable(mReviewId, flags);
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
