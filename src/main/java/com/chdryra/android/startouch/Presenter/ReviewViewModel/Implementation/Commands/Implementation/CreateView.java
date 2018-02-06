/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

abstract class CreateView implements LaunchViewCommand.ViewCreator {
    private final ReviewViewAdapter<?> mUnexpanded;
    private final FactoryReviewView mViewFactory;

    protected abstract ReviewView<?> createView(AdapterReviewNode<?> adapter);

    public CreateView(FactoryReviewView viewFactory, ReviewViewAdapter<?> unexpanded) {
        mViewFactory = viewFactory;
        mUnexpanded = unexpanded;
    }

    @Override
    @Nullable
    public ReviewView<?> newView() {
        AdapterReviewNode<?> adapter = null;
        try {
            adapter = (AdapterReviewNode<?>) mUnexpanded.expandGridData();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return adapter != null ? createView(adapter) : null;
    }

    public FactoryReviewView getViewFactory() {
        return mViewFactory;
    }
}
