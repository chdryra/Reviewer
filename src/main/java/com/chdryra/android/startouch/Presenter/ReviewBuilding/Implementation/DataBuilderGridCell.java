/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderGridCell<T extends GvDataParcelable> extends GvDataListImpl<T>
        implements DataObservable.DataObserver {

    public static final GvDataType<DataBuilderGridCell> TYPE =
            new GvDataType<>(DataBuilderGridCell.class, "create", "create");

    private final ReviewViewAdapter<T> mDataAdapter;
    private final FactoryVhDataCollection mViewHolderFactory;

    public DataBuilderGridCell(DataBuilderAdapter<T> dataAdapter,
                               FactoryVhDataCollection viewHolderFactory) {
        super(dataAdapter.getGvDataType(), new GvReviewId("create"));
        mDataAdapter = dataAdapter;
        mViewHolderFactory = viewHolderFactory;
        mDataAdapter.registerObserver(this);
        onDataChanged();
    }

    @Override
    public String toString() {
        return getGvDataType().getDataName();
    }

    @Override
    public ViewHolder getViewHolder() {
        boolean useDatumVh = size() == 1 && !getGvDataType().equals(GvImage.TYPE);
        return mViewHolderFactory.newViewHolder(useDatumVh ? get(0).getViewHolder() : null);
    }

    @Override
    public void onDataChanged() {
        clear();
        addAll(mDataAdapter.getGridData().toArrayList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBuilderGridCell)) return false;
        if (!super.equals(o)) return false;

        DataBuilderGridCell<?> that = (DataBuilderGridCell<?>) o;

        if (!mDataAdapter.equals(that.mDataAdapter)) return false;
        return mViewHolderFactory.equals(that.mViewHolderFactory);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mDataAdapter.hashCode();
        result = 31 * result + mViewHolderFactory.hashCode();
        return result;
    }
}
