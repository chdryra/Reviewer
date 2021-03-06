/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGridDataViewer;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateToData<T extends GvData> extends GridDataWrapperBasic<GvCanonical> {
    private final GvCanonicalCollection<T> mData;
    private final FactoryReviewViewAdapter mAdapterFactory;
    private final GridDataViewer<GvCanonical> mViewer;

    public ViewerAggregateToData(GvCanonicalCollection<T> aggregateData,
                                 FactoryGridDataViewer viewerfactory,
                                 FactoryReviewViewAdapter adapterFactory) {
        mData = aggregateData;
        mAdapterFactory = adapterFactory;
        mViewer = viewerfactory.newAggregateToReviewsViewer(mData);
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mData.getGvDataType();
    }

    @Override
    public GvDataList<GvCanonical> getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(GvCanonical datum) {
        return mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvCanonical datum) {
        ReviewViewAdapter adapter;
        if (!isExpandable(datum)) {
            adapter = null;
        } else if (datum.size() == 1) {
            return mViewer.expandGridCell(datum);
        } else {
            adapter = newDataToReviewsAdapter(datum);
        }

        return adapter;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mViewer.expandGridData();
    }

    FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    ReviewViewAdapter newDataToReviewsAdapter(GvCanonical datum) {
        return mAdapterFactory.newDataToReviewsAdapter(datum.toList(),
                datum.getCanonical().toString());
    }
}
