/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import android.app.Activity;
import android.app.DialogFragment;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
//TODO make this independent of Android
public interface UiTypeLauncher {
    void launch(DialogFragment launchableUI);

    void launch(Class<? extends Activity> activityClass, String argsKey);

    void launch(ReviewView<?> view);

    void launch(ReviewsListView view);
}
