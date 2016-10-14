/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Primary implementation of {@link ReviewViewAdapter}.
 */
public class ReviewViewAdapterImpl<T extends GvData> implements ReviewViewAdapter<T>,
        DataObservable.DataObserver {
    private final ArrayList<DataObserver> mObservers = new ArrayList<>();
    private GridDataWrapper<T> mWrapper;
    private ReviewView<T> mView;
    private boolean mIsAttached = false;

    public ReviewViewAdapterImpl() {
        mWrapper = null;
    }

    public ReviewViewAdapterImpl(GridDataWrapper<T> wrapper) {
        mWrapper = wrapper;
    }

    protected GridDataWrapper<T> getWrapper() {
        return mWrapper;
    }

    protected void setWrapper(GridDataWrapper<T> wrapper) {
        if (mWrapper != null) mWrapper.detachAdapter();
        attachToViewer(wrapper);
        notifyDataObservers();
    }

    protected void onAttach() {

    }

    protected void onDetach() {

    }

    @Override
    public void attachReviewView(ReviewView<T> view) {
        mView = view;
        registerObserver(mView);
        if (mWrapper != null && !mIsAttached) attachToViewer(mWrapper);
        onAttach();
    }

    @Override
    public void detachReviewView() {
        unregisterObserver(mView);
        mView = null;
        if (mWrapper != null && mIsAttached) detachFromViewer();
        onDetach();
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
    public void onDataChanged() {
        notifyDataObservers();
    }

    @Override
    public void registerObserver(DataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(DataObserver observer) {
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
        return "";
    }

    @Override
    public float getRating() {
        return 0;
    }

    @Override
    public void getCover(CoverCallback callback) {
        callback.onAdapterCover(new GvImage());
    }

    @Override
    public ReviewStamp getStamp() {
        return mWrapper != null ? mWrapper.getStamp() : ReviewStamp.noStamp();
    }

    private void attachToViewer(GridDataWrapper<T> wrapper) {
        mWrapper = wrapper;
        mWrapper.attachAdapter(this);
        mIsAttached = true;
    }

    private void detachFromViewer() {
        mWrapper.detachAdapter();
        mIsAttached = false;
    }
}
