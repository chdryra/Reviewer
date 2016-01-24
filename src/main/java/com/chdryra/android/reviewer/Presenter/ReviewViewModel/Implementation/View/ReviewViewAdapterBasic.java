/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;

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
    private ReviewView<T> mView;

    public void setViewer(GridDataViewer<T> wrapper) {
        mWrapper = wrapper;
        notifyGridDataObservers();
    }

    //Overridden
    @Override
    public void attachReviewView(ReviewView<T> view) {
        mView = view;
    }

    @Override
    public ReviewView<T> getReviewView() {
        return mView;
    }

    @Override
    public GvDataList<T> getGridData() {
        return mWrapper != null ? mWrapper.getGridData() : null;
    }

    @Override
    public boolean isExpandable(T datum) {
        return mWrapper != null && mWrapper.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(T datum) {
        return isExpandable(datum) ? mWrapper.expandGridCell(datum) : null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mWrapper != null ? mWrapper.expandGridData() : null;
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
}
