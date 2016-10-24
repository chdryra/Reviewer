/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.BuildUiLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherAndroid;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUiLauncher {
    private final Class<? extends Activity> mDefaultActivity;

    public FactoryUiLauncher(Class<? extends Activity> defaultActivity) {
        mDefaultActivity = defaultActivity;
    }

    public UiLauncherAndroid newLauncher(RepositorySuite repository,
                                         ReviewBuilderSuite builder,
                                         FactoryReviewView factoryReviewView,
                                         ReviewsSource masterRepo,
                                         LaunchableConfig buildConfig) {
        BuildUiLauncher buildScreenLauncher
                = new BuildUiLauncher(buildConfig, repository, new ItemPacker<Review>(), builder);

        FactoryReviewLauncher factoryReviewLauncher = new FactoryReviewLauncher(factoryReviewView, masterRepo);
        UiLauncherAndroid uiLauncher = new UiLauncherAndroid(buildScreenLauncher,
                factoryReviewLauncher, mDefaultActivity, new ItemPacker<ReviewView<?>>());
        buildScreenLauncher.setUiLauncher(uiLauncher);

        return uiLauncher;
    }
}
