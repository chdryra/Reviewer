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

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridDataWrapperBasic<T extends GvData> implements GridDataWrapper<T> {
    private ReviewViewAdapterBasic<T> mAdapter;
    private final ArrayList<DataObserver> mObservers = new ArrayList<>();

    protected void onAttach() {

    }

    protected void onDetach() {

    }

    @Override
    public void attachAdapter(ReviewViewAdapterBasic<T> adapter) {
        mAdapter = adapter;
        registerDataObserver(mAdapter);
        onAttach();
    }

    @Override
    public void detachAdapter() {
        unregisterDataObserver(mAdapter);
        onDetach();
        mAdapter = null;
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
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }
}
