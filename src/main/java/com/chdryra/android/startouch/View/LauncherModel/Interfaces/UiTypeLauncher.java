/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.LauncherModel.Interfaces;

import android.app.Activity;
import android.app.DialogFragment;

import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;

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
}
