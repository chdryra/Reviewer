/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewPerspective<T extends GvData> {
    private final ReviewViewAdapter<T> mAdapter;
    private final ReviewViewParams mParams;
    private final ReviewViewActions<T> mActions;

    public ReviewViewPerspective(ReviewViewAdapter<T> adapter,
                                 ReviewViewActions<T> actions,
                                 ReviewViewParams params) {
        mAdapter = adapter;
        mParams = params;
        mActions = actions;
    }

    public ReviewViewAdapter<T> getAdapter() {
        return mAdapter;
    }

    public ReviewViewParams getParams() {
        return mParams;
    }

    public ReviewViewActions<T> getActions() {
        return mActions;
    }

    public void attachToActions(ReviewView<T> view) {
        mActions.attachReviewView(view);
    }

    public void attachToAdapter(ReviewView<T> view) {
        mAdapter.attachReviewView(view);
    }

    public void detach() {
        mActions.detachReviewView();
        mAdapter.detachReviewView();
    }
}

