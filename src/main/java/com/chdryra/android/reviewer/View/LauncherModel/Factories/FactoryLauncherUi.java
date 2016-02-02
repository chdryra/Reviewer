/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.LauncherModel.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLauncherUi {
    private Class<? extends Activity> mReviewViewActivity;

    public FactoryLauncherUi(Class<? extends Activity> reviewViewActivity) {
        mReviewViewActivity = reviewViewActivity;
    }

    public LauncherUi newLauncher(Activity commissioner, int requestCode, String tag, Bundle args) {
        return new LauncherUiImpl(commissioner, mReviewViewActivity, requestCode, tag, args);
    }
}
