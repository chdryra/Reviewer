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
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataCollection;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonicalCollection<T extends GvData> extends AbstractCollection<GvCanonical>
        implements GvDataCollection<GvCanonical>{
    public static final Parcelable.Creator<GvCanonicalCollection> CREATOR = new Parcelable
            .Creator<GvCanonicalCollection>() {
        //Overridden
        @Override
        public GvCanonicalCollection createFromParcel(Parcel in) {
            return new GvCanonicalCollection(in);
        }

        @Override
        public GvCanonicalCollection[] newArray(int size) {
            return new GvCanonicalCollection[size];
        }
    };

    private GvReviewId mReviewId;
    private GvDataListImpl<GvCanonical> mData;
    private GvDataType<T> mType;
    private Comparator<GvCanonical> mComparator;

    //Constructors
    public GvCanonicalCollection(GvReviewId reviewId, GvDataType<T> type) {
        mReviewId = reviewId;
        mType = type;
        GvDataType<GvCanonical> listType =
                new GvDataType<>(GvCanonical.class, type.getDatumName(), type.getDataName());
        mData = new GvDataListImpl<>(listType, null);
        setComparator();
    }

    public GvCanonicalCollection(Parcel in) {
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mData = in.readParcelable(GvDataListImpl.class.getClassLoader());
        setComparator();
    }

    public boolean addCanonnical(GvCanonical<T> canonical) {
        return mData.add(canonical);
    }

    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }

    @Override
    public boolean add(GvCanonical datum) {
        //TODO make type safe
        return datum.getGvDataType().equals(mType) && addCanonnical(datum);
    }

    private void setComparator() {
        final Comparator<? super T> comparator = GvDataComparators.getDefaultComparator(mType);
        mComparator = new Comparator<GvCanonical>() {
            //Overridden
            @Override
            public int compare(GvCanonical lhs, GvCanonical rhs) {
                //TODO make type safe
                return comparator.compare((T) lhs.getCanonical(), (T) rhs.getCanonical());
            }
        };
    }

    //Overridden
    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public void sort() {
        mData.sort(mComparator);
    }

    @Override
    public GvCanonical<T> getItem(int position) {
        //TODO make type safe
        return mData.getItem(position);
    }

    @Nullable
    @Override
    public GvCanonical<T> getItem(ReviewId id) {
        //TODO make type safe
        for (GvCanonical<T> datum : this) {
            if(datum.getReviewId().equals(id)) return datum;
        }

        return null;
    }

    public T getCanonical(int position) {
        return getItem(position).getCanonical();
    }

    @Override
    public GvDataListImpl<GvCanonical> toList() {
        return mData;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return mData.getStringSummary();
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasElements() {
        return mData.hasElements();
    }

    @Override
    public boolean isVerboseCollection() {
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mType, flags);
        dest.writeParcelable(mData, flags);
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
    public boolean hasData(DataValidator dataValidator) {
        return size() > 0;
    }

    @Override
    public Iterator<GvCanonical> iterator() {
        return mData.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvCanonicalCollection)) return false;

        GvCanonicalCollection<?> that = (GvCanonicalCollection<?>) o;

        if (!mData.equals(that.mData)) return false;
        return mType.equals(that.mType);

    }

    @Override
    public int hashCode() {
        int result = mData.hashCode();
        result = 31 * result + mType.hashCode();
        return result;
    }
}
