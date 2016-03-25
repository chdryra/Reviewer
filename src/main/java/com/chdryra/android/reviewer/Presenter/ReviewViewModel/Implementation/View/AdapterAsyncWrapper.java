/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeAsync;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterAsyncWrapper<T extends GvData> implements ReviewViewAdapter<T>, ReviewNode.NodeObserver {
    private ReviewNodeAsync<?> mTrigger;
    private ReviewViewAdapter<T> mAdapter;

    public AdapterAsyncWrapper(ReviewNodeAsync<?> trigger, ReviewViewAdapter<T> adapter) {
        mTrigger = trigger;
        mAdapter = adapter;
        mTrigger.registerNodeObserver(this);
    }

    @Override
    public void attachReviewView(ReviewView<T> view) {
        mAdapter.attachReviewView(view);
    }

    @Override
    public ReviewView<T> getReviewView() {
        return mAdapter.getReviewView();
    }

    @Override
    public String getSubject() {
        return mAdapter.getSubject();
    }

    @Override
    public float getRating() {
        return mAdapter.getRating();
    }

    @Override
    public GvImageList getCovers() {
        return mAdapter.getCovers();
    }

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return mAdapter.getGvDataType();
    }

    @Override
    public GvDataList<T> getGridData() {
        return mAdapter.getGridData();
    }

    @Override
    public boolean isExpandable(T datum) {
        return mAdapter.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(T datum) {
        return mAdapter.expandGridCell(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mAdapter.expandGridData();
    }

    @Override
    public void registerDataObserver(DataObserver observer) {
        mAdapter.registerDataObserver(observer);
    }

    @Override
    public void unregisterDataObserver(DataObserver observer) {
        mAdapter.unregisterDataObserver(observer);
    }

    @Override
    public void notifyDataObservers() {
        mAdapter.notifyDataObservers();
    }

    @Override
    public void onNodeChanged() {
        notifyDataObservers();
    }
}
