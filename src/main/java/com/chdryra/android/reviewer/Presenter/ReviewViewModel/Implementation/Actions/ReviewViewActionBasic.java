/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.app.Activity;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ReviewViewAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation
        .UnattachedReviewViewException;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewViewActionBasic<T extends GvData> implements ReviewViewAction<T> {
    private ReviewView<T> mReviewView;

    @Override
    public ReviewView<T> getReviewView() {
        return mReviewView;
    }

    @Override
    public ReviewViewAdapter<T> getAdapter() {
        return mReviewView.getAdapter();
    }

    @Override
    public Activity getActivity() {
        if (mReviewView == null) {
            throw new UnattachedReviewViewException("Can't getActivity(): No ReviewView Attached");
        }

        return mReviewView.getActivity();
    }

    @Override
    public void attachReviewView(ReviewView<T> reviewView) {
        if (mReviewView != null) onUnattachReviewView();
        mReviewView = reviewView;
        onAttachReviewView();
    }

    @Override
    public void onUnattachReviewView() {

    }

    @Override
    public void onAttachReviewView() {

    }

    protected GvDataList<T> getGridData() {
        return getReviewView().getGridData();
    }
}
