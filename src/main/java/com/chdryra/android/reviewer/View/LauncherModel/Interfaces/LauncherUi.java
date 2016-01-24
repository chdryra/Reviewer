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

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface LauncherUi {
    int getRequestCode();

    Activity getCommissioner();

    void launch(DialogFragment launchableUI);

    void launch(Class<? extends Activity> activityClass, String argsKey);
}
