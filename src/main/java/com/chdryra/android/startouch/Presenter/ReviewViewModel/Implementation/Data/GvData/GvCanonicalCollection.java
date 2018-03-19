/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
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
        implements GvDataCollection<GvCanonical> {
    private final GvReviewId mReviewId;
    private final GvDataList<GvCanonical> mData;
    private final GvDataType<T> mType;
    private Comparator<GvCanonical> mComparator;

    //Constructors
    public GvCanonicalCollection(GvReviewId reviewId, GvDataType<T> type) {
        mReviewId = reviewId;
        mType = type;
        GvDataType<GvCanonical> listType =
                new GvDataType<>(GvCanonical.class, type.getDatumName(), type.getDataName());
        mData = new GvDataListImpl<>(listType, new GvReviewId());
        setComparator();
    }

    public boolean addCanonnical(GvCanonical<T> canonical) {
        return mData.add(canonical);
    }

    public T getCanonical(int position) {
        return get(position).getCanonical();
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return null;
    }

    @Override
    public DataSize getDataSize() {
        return new GvSize(getGvReviewId(), mType, size());
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
    public GvCanonical<T> get(int position) {
        //TODO make type safe
        return mData.get(position);
    }

    @Override
    public GvDataList<GvCanonical> toList() {
        return mData;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String toString() {
        return mData.toString();
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
    public boolean isCollection() {
        return true;
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
}
