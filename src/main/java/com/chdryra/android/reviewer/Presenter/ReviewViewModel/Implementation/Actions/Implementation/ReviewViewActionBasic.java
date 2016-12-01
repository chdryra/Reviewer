/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.util.Log;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ReviewViewAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.UnattachedReviewViewException;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActionBasic<T extends GvData> implements ReviewViewAction<T> {
    private ReviewView<T> mReviewView;

    protected GvDataList<T> getGridData() {
        return getReviewView().getAdapterData();
    }

    @Override
    public ReviewView<T> getReviewView() {
        throwIfNoReviewViewAttached();
        return mReviewView;
    }

    @Override
    public ReviewViewAdapter<T> getAdapter() {
        return getReviewView().getAdapter();
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return getReviewView().getCurrentScreen();
    }

    @Override
    public void attachReviewView(ReviewView<T> reviewView) {
        if (mReviewView != null) detachReviewView();
        mReviewView = reviewView;
        onAttachReviewView();
    }

    @Override
    public void detachReviewView() {
        Log.i("ReviewViewAction", "ReviewView detached");
        onDetachReviewView();
        mReviewView = null;
    }

    @Override
    public void onDetachReviewView() {

    }

    @Override
    public void onAttachReviewView() {

    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return false;
    }

    private void throwIfNoReviewViewAttached() {
        if (mReviewView == null) {
            throw new UnattachedReviewViewException("   No ReviewView Attached");
        }
    }
}
