/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

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
    public String getStringSummary() {
        return getGvDataType().getDataName();
    }

    @Override
    public ViewHolder getViewHolder() {
        return mViewHolderFactory.newViewHolder();
    }

    @Override
    public void onDataChanged() {
        mData = mDataAdapter.getGridData().toArrayList();
    }
}
