/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewBuilderSuiteAndroid implements ReviewBuilderSuite, ReviewEditor.BuildListener {
    private final FactoryReviewView mViewFactory;
    private ReviewEditor<?> mReviewEditor;

    public ReviewBuilderSuiteAndroid(FactoryReviewView viewFactory) {
        mViewFactory = viewFactory;
    }

    @Override
    public ReviewEditor<?> newReviewEditor(ReviewEditor.EditMode editMode, LocationClient client, @Nullable Review template) {
        mReviewEditor = mViewFactory.newEditor(editMode, client, template);
        return mReviewEditor;
    }

    @Override
    public ReviewEditor<?> getReviewEditor() {
        return mReviewEditor;
    }

    @Override
    public void onReviewBuilt() {
        discardReviewEditor();
    }

    @Override
    public void discardReviewEditor() {
        mReviewEditor = null;
    }
}
