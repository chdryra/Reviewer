/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.Activity;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewAction {
    private ReviewView mReviewView;

    //public methods
    public ReviewView getReviewView() {
        return mReviewView;
    }

    public ReviewViewAdapter getAdapter() {
        return mReviewView.getAdapter();
    }

    public Activity getActivity() {
        return mReviewView != null ? mReviewView.getActivity() : null;
    }

    public void attachReviewView(ReviewView reviewView) {
        if (mReviewView != null) onUnattachReviewView();
        mReviewView = reviewView;
        onAttachReviewView();
    }

    public void onUnattachReviewView() {

    }

    public void onAttachReviewView() {

    }

    //protected methods
    protected GvDataList getGridData() {
        return getReviewView().getGridData();
    }
}
