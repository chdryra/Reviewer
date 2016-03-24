/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewPerspective<T extends GvData> {
    private ReviewViewAdapter<T> mAdapter;
    private ReviewViewParams mParams;
    private ReviewViewActions<T> mActions;
    private ReviewViewModifier mModifier;

    public ReviewViewPerspective(ReviewViewAdapter<T> adapter,
                                 ReviewViewActions<T> actions,
                                 ReviewViewParams params) {
        this(adapter, actions, params, null);
    }

    public ReviewViewPerspective(ReviewViewAdapter<T> adapter,
                                 ReviewViewActions<T> actions,
                                 ReviewViewParams params,
                                 ReviewViewModifier modifier) {
        mAdapter = adapter;
        mParams = params;
        mActions = actions;
        mModifier = modifier;
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

    public ReviewViewModifier getModifier() {
        return mModifier;
    }
}

