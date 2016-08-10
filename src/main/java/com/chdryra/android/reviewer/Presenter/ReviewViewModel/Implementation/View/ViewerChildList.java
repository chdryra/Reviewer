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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRefList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewerChildList extends ViewerNodeBasic<GvReviewRef> {
    private static final GvDataType<GvReviewRef> TYPE = GvReviewRef.TYPE;

    private DataConverter<ReviewReference, GvReviewRef, GvReviewRefList> mConverter;
    private FactoryReviewViewAdapter mAdapterFactory;

    public ViewerChildList(ReviewNode node,
                           DataConverter<ReviewReference, GvReviewRef, GvReviewRefList> converter,
                           FactoryReviewViewAdapter adapterFactory) {
        super(node, TYPE);
        mConverter = converter;
        mAdapterFactory = adapterFactory;
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        nullifyCache();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        nullifyCache();
    }

    @Override
    protected GvDataList<GvReviewRef> makeGridData() {
        return  mConverter.convert(getReviewNode().getChildren());
    }

    @Override
    protected void onDetach() {
        GvReviewRefList cache = (GvReviewRefList) getCache();
        if(cache != null) cache.unbind();
        super.onDetach();
    }

    @Override
    public boolean isExpandable(GvReviewRef datum) {
        return getReviewNode().hasChild(datum.getReviewId());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvReviewRef datum) {
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
