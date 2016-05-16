/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import android.app.Activity;

import com.chdryra.android.reviewer.View.Configs.AddEditViewClasses;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LaunchablesList {
    //TODO make this independent of Android
    Class<? extends Activity> getDefaultReviewViewActivity();

    Class<? extends LaunchableUi> getLoginUi();

    Class<? extends LaunchableUi> getSignUpUi();

    Class<? extends LaunchableUi> getFeedUi();

    Class<? extends LaunchableUi> getReviewBuilderUi();

    Class<? extends LaunchableUi> getMapEditorUi();

    Class<? extends LaunchableUi> getShareReviewUi();

    Class<? extends LaunchableUiAlertable> getShareEditReviewUi();

    Iterable<AddEditViewClasses<?>> getDataLaunchableUis();
}
