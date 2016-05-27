/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUiLauncher {
    private Class<? extends Activity> mReviewViewActivity;

    public FactoryUiLauncher(Class<? extends Activity> reviewViewActivity) {
        mReviewViewActivity = reviewViewActivity;
    }

    public UiLauncher newLauncher(Activity activity) {
        return new UiLauncher(activity, mReviewViewActivity);
    }
}
