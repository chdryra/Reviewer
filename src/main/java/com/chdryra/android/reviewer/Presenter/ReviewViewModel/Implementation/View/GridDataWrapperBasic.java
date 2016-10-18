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

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridDataWrapperBasic<T extends GvData> extends DataObservableDefault implements GridDataWrapper<T> {
    private ReviewViewAdapterImpl<T> mAdapter;

    void onAttach() {

    }

    void onDetach() {

    }

    @Override
    public void attachAdapter(ReviewViewAdapterImpl<T> adapter) {
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
}
