/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is {@link GvReviewOverviewList}.
 */
public class ViewerChildList implements GridDataViewer<GvReviewOverviewList.GvReviewOverview> {
    private static final GvDataType<GvReviewOverviewList.GvReviewOverview> TYPE
            = GvReviewOverviewList.GvReviewOverview.TYPE;

    private ReviewNode mNode;
    private DataConverter<Review, GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> mConverter;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerChildList(ReviewNode node,
                           DataConverter<Review, GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList> converter,
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
    public boolean isExpandable(GvReviewOverviewList.GvReviewOverview datum) {
        return mNode.hasChild(datum.getReviewId());
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvReviewOverviewList.GvReviewOverview datum) {
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
