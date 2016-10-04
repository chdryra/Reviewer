/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncherImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUiLauncher {
    private final Class<? extends Activity> mDefaultActivity;
    private final Class<? extends Activity> mReviewsListActivity;

    public FactoryUiLauncher(Class<? extends Activity> defaultActivity, Class<? extends Activity>
            reviewsListActivity) {
        mDefaultActivity = defaultActivity;
        mReviewsListActivity = reviewsListActivity;
    }

    public UiLauncherAndroid newLauncher(RepositorySuite repository,
                                         ReviewBuilderSuite builder,
                                         FactoryReviewView factoryReviewView,
                                         ReviewsSource masterRepo,
                                         LaunchableConfig buildConfig) {
        BuildScreenLauncherImpl buildScreenLauncher = new BuildScreenLauncherImpl(buildConfig, repository, builder);
        FactoryReviewLauncher factoryReviewLauncher = new FactoryReviewLauncher(factoryReviewView, masterRepo);
        UiLauncherAndroid uiLauncher = new UiLauncherAndroid(buildScreenLauncher, factoryReviewLauncher, mDefaultActivity, mReviewsListActivity);
        buildScreenLauncher.setUiLauncher(uiLauncher);

        return uiLauncher;
    }
}
