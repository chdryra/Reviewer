/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is {@link GvReviewOverviewList}.
 */
public class ViewerChildList implements GridDataViewer<GvReviewOverview> {
    private static final GvDataType<GvReviewOverview> TYPE
            = GvReviewOverview.TYPE;

    private ReviewNode mNode;
    private DataConverter<Review, GvReviewOverview, GvReviewOverviewList> mConverter;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerChildList(ReviewNode node,
                           DataConverter<Review, GvReviewOverview, GvReviewOverviewList> converter,
                           FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mConverter = converter;
        mAdapterFactory = adapterFactory;
    }

    private ReviewViewAdapter newNodeDataAdapter(ReviewNode node) {
        return mAdapterFactory.newNodeDataAdapter(node);
    }

    //Overridden
    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return TYPE;
    }

    @Override
    public GvReviewOverviewList getGridData() {
        return mConverter.convert(mNode.getChildren());
    }

    @Override
    public boolean isExpandable(GvReviewOverview datum) {
        return mNode.hasChild(datum.getReviewId());
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvReviewOverview datum) {
        if (isExpandable(datum)) {
            return newNodeDataAdapter(mNode.getChild(datum.getReviewId()));
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return newNodeDataAdapter(mNode);
    }
}
