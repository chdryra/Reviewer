/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 February, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Primary implementation of {@link ReviewViewAdapter}.
 */
public abstract class ReviewViewAdapterBasic<T extends GvData> implements ReviewViewAdapter<T> {
    final ArrayList<GridDataObservable.GridDataObserver> mObservers = new ArrayList<>();
    private GridDataViewer<T> mWrapper;
    private ReviewView mView;

    @Override
    public GvDataList<T> getGridData() {
        return mWrapper != null ? mWrapper.getGridData() : null;
    }

    @Override
    public void registerReviewView(ReviewView view) {
        mView = view;
    }

    @Override
    public ReviewView getReviewView() {
        return mView;
    }

    @Override
    public void registerGridDataObserver(GridDataObservable.GridDataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterGridDataObserver(GridDataObservable.GridDataObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void notifyGridDataObservers() {
        for (GridDataObservable.GridDataObserver observer : mObservers) {
            observer.onGridDataChanged();
        }
    }

    @Override
    public boolean isExpandable(T datum) {
        return mWrapper != null && mWrapper.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
        return isExpandable(datum) ? mWrapper.expandGridCell(datum) : null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mWrapper.expandGridData();
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        if (mWrapper != null) mWrapper.setData(data);
        notifyGridDataObservers();
    }

    public void setViewer(GridDataViewer<T> wrapper) {
        mWrapper = wrapper;
        notifyGridDataObservers();
    }
}
