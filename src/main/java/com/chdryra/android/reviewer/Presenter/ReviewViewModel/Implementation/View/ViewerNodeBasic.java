/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class ViewerNodeBasic<T extends GvData> extends GridDataWrapperBasic<T>
        implements ReviewNode.NodeObserver {
    private ReviewNode mNode;
    private GvDataList<T> mCache;
    private GvDataType<?> mType;

    protected abstract GvDataList<T> makeGridData();

    public ViewerNodeBasic(ReviewNode node, GvDataType<?> type) {
        mNode = node;
        mType = type;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mNode.registerObserver(this);
    }

    @Override
    protected void onDetach() {
        mNode.unregisterObserver(this);
        super.onDetach();
    }

    @Override
    public GvDataType<?> getGvDataType() {
        return mType;
    }


    @Override
    public void onChildAdded(ReviewNode child) {

    }

    @Override
    public void onChildRemoved(ReviewNode child) {

    }

    @Override
    public void onNodeChanged() {

    }

    @Override
    public void onDescendantsChanged() {
        nullifyCache();
    }

    protected void nullifyCache() {
        mCache = null;
        notifyDataObservers();
    }

    @Override
    public GvDataList<T> getGridData() {
        if(mCache == null) mCache = makeGridData();

        return mCache;
    }

    protected ReviewNode getReviewNode() {
        return mNode;
    }

    @Nullable
    protected GvDataList<T> getCache() {
        return mCache;
    }
}
