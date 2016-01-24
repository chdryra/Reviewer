/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerDataToData<T extends GvData> implements GridDataViewer<T> {
    private ReviewNode mNode;
    private GvDataCollection<T> mData;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerDataToData(ReviewNode node,
                            GvDataCollection<T> data,
                            FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mData = data;
        mAdapterFactory = adapterFactory;
    }

    //Overridden

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
        if (isExpandable(datum)) {
            return mAdapterFactory.newDataToDataAdapter(mNode, (GvDataCollection) datum);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mAdapterFactory.newNodeDataAdapter(mData);
    }
}

