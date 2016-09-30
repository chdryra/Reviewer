/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncherImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewLauncher {
    private FactoryReviewView mFactoryReviewView;
    private ReviewsSource mMasterRepo;

    public FactoryReviewLauncher(FactoryReviewView factoryReviewView, ReviewsSource masterRepo) {
        mFactoryReviewView = factoryReviewView;
        mMasterRepo = masterRepo;
    }

    public ReviewLauncher newReviewLauncher(UiLauncher launcher, AuthorId sessionAuthor) {
        return new ReviewLauncherImpl(mMasterRepo, launcher, mFactoryReviewView, sessionAuthor);
    }

}
