/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncherImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewLauncher {
    private FactoryReviewView mFactoryReviewView;
    private LaunchableConfig mAuthorsConfig;

    public FactoryReviewLauncher(FactoryReviewView factoryReviewView, LaunchableConfig
            authorsConfig) {
        mFactoryReviewView = factoryReviewView;
        mAuthorsConfig = authorsConfig;
    }

    public ReviewLauncher newReviewLauncher(ReviewsSource masterRepo, UiLauncher launcher) {
        return new ReviewLauncherImpl(masterRepo, launcher, mFactoryReviewView, mAuthorsConfig);
    }

}
