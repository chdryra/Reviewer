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
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UiLauncher {
    private Activity mCommissioner;
    private Class<? extends Activity> mReviewViewActivity;

    public UiLauncher(Activity commissioner, Class<? extends Activity> reviewViewActivity) {
        mCommissioner = commissioner;
        mReviewViewActivity = reviewViewActivity;
    }

    public void launch(LaunchableUi ui, int requestCode, Bundle args) {
        ui.launch(new LauncherUiImpl(mCommissioner, mReviewViewActivity, requestCode, ui.getLaunchTag(), args));
    }

    public void launch(LaunchableUi ui, int requestCode){
        launch(ui, requestCode, new Bundle());
    }

    public void launch(LaunchableConfig config, int requestCode, Bundle args) {
        launch(config.getLaunchable(), requestCode, args);
    }

    public void launch(LaunchableConfig config, int requestCode) {
        launch(config.getLaunchable(), requestCode, new Bundle());
    }
}
