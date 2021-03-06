/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ReviewViewAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation
        .UnattachedReviewViewException;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActionBasic<T extends GvData> implements ReviewViewAction<T> {
    private ReviewView<T> mReviewView;
    private String mToastOnAttach;

    protected void showToast(String message) {
        if (isAttached()) {
            getCurrentScreen().showToast(message);
        } else {
            mToastOnAttach = message;
        }
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
        onDetachReviewView();
        mReviewView = null;
    }

    @Override
    public void onDetachReviewView() {

    }

    @Override
    public void onAttachReviewView() {
        if (mToastOnAttach != null) {
            showToast(mToastOnAttach);
            mToastOnAttach = null;
        }
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return false;
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return false;
    }

    private boolean isAttached() {
        return mReviewView != null;
    }

    private void throwIfNoReviewViewAttached() {
        if (!isAttached()) {
            throw new UnattachedReviewViewException("No ReviewView Attached");
        }
    }
}
