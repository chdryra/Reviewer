/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerDataToData<T extends GvData> extends GridDataWrapperBasic<T> {
    private final GvDataCollection<T> mData;
    private final FactoryReviewViewAdapter mAdapterFactory;

    public ViewerDataToData(GvDataCollection<T> data,
                            FactoryReviewViewAdapter adapterFactory) {
        mData = data;
        mAdapterFactory = adapterFactory;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return getGridData().getGvDataType();
    }

    @Override
    public GvDataList<T> getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(T datum) {
        return datum.hasElements() && mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
//        if (isExpandable(datum)) {
//            return mAdapterFactory.newDataToDataAdapter(mNode, (GvDataCollection) datum);
//        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
        //return mAdapterFactory.newTreeSummaryAdapter(mData);
    }
}
