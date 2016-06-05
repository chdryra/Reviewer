/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;

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
    private final ArrayList<DataObserver> mObservers = new ArrayList<>();
    private GridDataViewer<T> mWrapper;
    private ReviewView<T> mView;

    public void setViewer(GridDataViewer<T> wrapper) {
        mWrapper = wrapper;
        notifyDataObservers();
    }

    @Override
    public void attachReviewView(ReviewView<T> view) {
        mView = view;
    }

    @Override
    public ReviewView<T> getReviewView() {
        return mView;
    }

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return mWrapper.getGvDataType();
    }

    @Override
    public GvDataList<T> getGridData() {
        return mWrapper.getGridData();
    }

    @Override
    public boolean isExpandable(T datum) {
        return mWrapper.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(T datum) {
        return isExpandable(datum) ? mWrapper.expandGridCell(datum) : null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mWrapper.expandGridData();
    }

    @Override
    public void registerDataObserver(DataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterDataObserver(DataObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void notifyDataObservers() {
        for (DataObserver observer : mObservers) {
            observer.onDataChanged();
        }
    }

    @Override
    public String getSubject() {
        return null;
    }

    @Override
    public float getRating() {
        return 0;
    }

    @Override
    public GvImageList getCovers() {
        return null;
    }

    @Override
    public ReviewStamp getStamp() {
        return mWrapper != null ? mWrapper.getStamp() : ReviewStamp.noStamp();
    }
}
