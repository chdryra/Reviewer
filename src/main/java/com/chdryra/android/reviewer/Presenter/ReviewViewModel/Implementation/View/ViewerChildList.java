/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNodeList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewerChildList extends ViewerNodeBasic<GvNode> {
    private static final GvDataType<GvNode> TYPE = GvNode.TYPE;

    private DataConverter<ReviewNode, GvNode, GvNodeList> mConverter;
    private FactoryReviewViewAdapter mAdapterFactory;

    public ViewerChildList(ReviewNode node,
                           DataConverter<ReviewNode, GvNode, GvNodeList> converter,
                           FactoryReviewViewAdapter adapterFactory) {
        super(node, TYPE);
        mConverter = converter;
        mAdapterFactory = adapterFactory;
    }

    @Override
    protected GvDataList<GvNode> makeGridData() {
        return  mConverter.convert(getReviewNode().getChildren());
    }

    @Override
    protected void onDetach() {
        GvNodeList cache = (GvNodeList) getCache();
        if(cache != null) cache.unbind();
        super.onDetach();
    }

    @Override
    public boolean isExpandable(GvNode datum) {
        return getReviewNode().hasChild(datum.getReviewId());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvNode datum) {
        ReviewNode child = getReviewNode().getChild(datum.getReviewId());
        return child != null ? newNodeDataAdapter(child) : null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return newNodeDataAdapter(getReviewNode());
    }

    private ReviewViewAdapter<?> newNodeDataAdapter(ReviewNode node) {
        return mAdapterFactory.newNodeDataAdapter(node);
    }
}
