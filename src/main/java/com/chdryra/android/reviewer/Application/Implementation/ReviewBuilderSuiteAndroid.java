/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.os.Bundle;
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

public class ReviewBuilderSuiteAndroid implements ReviewBuilderSuite {
    private FactoryReviewView mViewFactory;
    private ReviewEditor<?> mReviewEditor;
    private ReviewPacker mReviewPacker;

    public ReviewBuilderSuiteAndroid(FactoryReviewView viewFactory, ReviewPacker reviewPacker) {
        mViewFactory = viewFactory;
        mReviewPacker = reviewPacker;
    }

    @Override
    public ReviewEditor<?> newReviewEditor(LocationClient client, @Nullable Review template) {
        mReviewEditor = mViewFactory.newEditor(template, client);
        return mReviewEditor;
    }

    @Override
    public ReviewEditor<?> getReviewEditor() {
        return mReviewEditor;
    }

    @Override
    public Review executeReviewEditor() {
        Review published = mReviewEditor.buildReview();
        discardReviewEditor();

        return published;
    }

    @Override
    public void discardReviewEditor() {
        mReviewEditor = null;
    }

    @Nullable
    @Override
    public Review unpackTemplate(Bundle args) {
        return mReviewPacker.unpackReview(args);
    }
}
