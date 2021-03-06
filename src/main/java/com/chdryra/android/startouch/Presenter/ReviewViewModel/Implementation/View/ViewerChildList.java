/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvNodeList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewerChildList extends ViewerNodeBasic<GvNode> {
    private static final GvDataType<GvNode> TYPE = GvNode.TYPE;

    private final DataConverter<ReviewNode, GvNode, GvNodeList> mConverter;
    private final FactoryReviewViewAdapter mAdapterFactory;

    public ViewerChildList(ReviewNode node,
                           DataConverter<ReviewNode, GvNode, GvNodeList> converter,
                           FactoryReviewViewAdapter adapterFactory) {
        super(node, TYPE);
        mConverter = converter;
        mAdapterFactory = adapterFactory;
    }

    @Override
    protected GvDataList<GvNode> makeGridData() {
        return mConverter.convert(getReviewNode().getChildren());
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        GvDataList<GvNode> cache = getCache();
        if (cache == null) cache = makeGridData();
        cache.add(mConverter.convert(child));
        setCache(cache);
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        GvDataList<GvNode> cache = getCache();
        if (cache == null) {
            cache = makeGridData();
        } else {
            GvNode toRemove = null;
            for (GvNode item : cache) {
                if (item.getNode().equals(child)) {
                    toRemove = item;
                    break;
                }
            }
            if (toRemove != null) {
                toRemove.unbind();
                cache.remove(toRemove);
            }
        }
        setCache(cache);
    }

    @Override
    public void onNodeChanged() {
        notifyDataObservers();
    }

    @Override
    protected void onNullifyCache() {
        GvDataList<GvNode> cache = getCache();
        if (cache != null) {
            for (GvNode gvNode : cache) {
                gvNode.unbind();
            }
        }
    }

    @Override
    protected void onDetach() {
        GvNodeList cache = (GvNodeList) getCache();
        if (cache != null) cache.unbind();
        super.onDetach();
    }

    @Override
    public boolean isExpandable(GvNode datum) {
        return getReviewNode().hasChild(datum.getReviewId());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvNode datum) {
        ReviewNode child = getReviewNode().getChild(datum.getReviewId());
        return child != null ? newNodeAdapter(child) : null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return newNodeAdapter(getReviewNode());
    }

    FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    private ReviewViewAdapter<?> newNodeAdapter(ReviewNode node) {
        return mAdapterFactory.newSummaryAdapter(node);
    }
}
