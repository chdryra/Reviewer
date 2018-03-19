/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.NullDataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SubscribableReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Primary implementation of {@link ReviewViewAdapter}.
 */
public abstract class ReviewViewAdapterBasic<T extends GvData> extends DataObservableDefault
        implements ReviewViewAdapter<T>, DataObservable.DataObserver {

    private GridDataWrapper<T> mWrapper;
    private ReviewView<T> mView;
    private boolean mIsAttached = false;
    private SubscribableReference<GvDataList<T>> mReference;

    public ReviewViewAdapterBasic(@Nullable GridDataWrapper<T> wrapper) {
        mWrapper = wrapper;
        mReference = new SubscribableReference<GvDataList<T>>() {
            @Override
            protected void doDereferencing(DereferenceCallback<GvDataList<T>> callback) {
                callback.onDereferenced(new DataValue<>(getGridData()));
            }
        };
    }

    protected ReviewViewAdapterBasic() {
        this(null);
    }

    protected GridDataWrapper<T> getWrapper() {
        return mWrapper;
    }

    protected void setWrapper(GridDataWrapper<T> wrapper) {
        if (mWrapper != null) mWrapper.detachAdapter();
        attachToViewer(wrapper);
        notifyDataObservers();
    }

    protected void notifySubscribers() {
        mReference.notifySubscribers();
    }

    protected void onAttach() {

    }

    protected void onDetach() {

    }

    @Override
    public DataReference<GvDataList<T>> getGridDataReference() {
        return mReference;
    }

    @Override
    protected void notifyDataObservers() {
        super.notifyDataObservers();
        notifySubscribers();
    }

    @Override
    public void onDataChanged() {
        notifyDataObservers();
    }

    @Override
    public void attachReviewView(ReviewView<T> view) {
        if (mView != null) detachReviewView(false);
        mView = view;
        registerObserver(mView);
        if (mWrapper != null && !mIsAttached) attachToViewer(mWrapper);
        onAttach();
    }

    @Override
    public void detachReviewView() {
        detachReviewView(true);
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
    public DataReference<Bitmap> getCoverReference() {
        return new NullDataReference<>();
    }

    @Override
    public ReviewStamp getStamp() {
        return mWrapper != null ? mWrapper.getStamp() : ReviewStamp.noStamp();
    }

    @Override
    public void sort(Comparator<? super T> comparator, OnSortedCallback callback) {
        if (mWrapper != null) mWrapper.sort(comparator, callback);
    }

    private void detachReviewView(boolean detachViewer) {
        unregisterObserver(mView);
        mView = null;
        if (detachViewer && mWrapper != null && mIsAttached) detachFromViewer();
        onDetach();
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
