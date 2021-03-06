/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridDataWrapperBasic<T extends GvData> extends DataObservableDefault
        implements GridDataWrapper<T> {
    private ReviewViewAdapterBasic<T> mAdapter;

    @Override
    public void attachAdapter(ReviewViewAdapterBasic<T> adapter) {
        mAdapter = adapter;
        registerObserver(mAdapter);
        onAttach();
    }

    @Override
    public void detachAdapter() {
        unregisterObserver(mAdapter);
        onDetach();
        mAdapter = null;
    }

    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }

    @Override
    public void sort(Comparator<? super T> comparator, OnSortedCallback callback) {
        callback.onSorted(CallbackMessage.ok());
    }

    void onAttach() {

    }

    void onDetach() {

    }
}
