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
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.EditUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.NodeLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewLauncherImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherAndroid;

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
                                         ReviewEditorSuite builder,
                                         FactoryReviewView viewFactory,
                                         ReviewsSource masterRepo,
                                         LaunchableConfig buildConfig,
                                         LaunchableConfig formatter,
                                         LaunchableConfig nodeMapper) {

        EditUiLauncher buildUi = new EditUiLauncher(buildConfig, builder, repository);
        NodeLauncher formatted = new NodeLauncher(formatter);
        NodeLauncher mapped = new NodeLauncher(nodeMapper);
        ReviewLauncherImpl reviewLauncher
                = new ReviewLauncherImpl(masterRepo, formatted, mapped, viewFactory);

        return new UiLauncherAndroid(buildUi, reviewLauncher, mDefaultActivity);
    }
}
