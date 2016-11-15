/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import android.app.Activity;

import com.chdryra.android.reviewer.View.Configs.Implementation.DataLaunchables;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LaunchablesList {
    //TODO make this independent of Android
    Class<? extends Activity> getDefaultActivity();

    Class<? extends LaunchableUi> getLogin();

    Class<? extends LaunchableUi> getSignUp();

    Class<? extends LaunchableUi> getFeed();

    Class<? extends LaunchableUi> getReviewBuild();

    Class<? extends LaunchableUi> getReviewFormatted();

    Class<? extends LaunchableUi> getMapperEdit();

    Class<? extends LaunchableUi> getMapperNode();

    Class<? extends LaunchableUi> getPublish();

    Class<? extends LaunchableUi> getReviewOptions();

    Iterable<DataLaunchables<?>> getDataLaunchables();
}
