/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer.View.Launcher.Implementation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Knows how to launch a {@link LaunchableUi} depending on whether
 * it is a Dialog or Activity underneath.
 */
public class LauncherUiImpl implements LauncherUi {
    private static final String LAUNCHER_ARGS = "com.chdryra.android.review.args_key";
    private final Activity mCommissioner;
    private final int mRequestCode;
    private final String mTag;
    private final Bundle mArgs;

    public LauncherUiImpl(Activity commissioner, int requestCode, String tag, Bundle args) {
        mCommissioner = commissioner;
        mRequestCode = requestCode;
        mTag = tag;
        mArgs = args;
    }

    public static Bundle getArgsForActivity(Activity launchableUI) {
        return launchableUI.getIntent().getBundleExtra(LAUNCHER_ARGS);
    }

    @Override
    public void launch(DialogFragment launchableUI) {
        DialogShower.show(launchableUI, mCommissioner, mRequestCode, mTag, mArgs);
    }

    @Override
    public void launch(Activity launchableUI) {
        Intent i = new Intent(mCommissioner, launchableUI.getClass());
        i.putExtra(LAUNCHER_ARGS, mArgs);
        mCommissioner.startActivityForResult(i, mRequestCode);
    }

    @Override
    public void launch(ReviewView reviewView) {
        Activity activity = mCommissioner;
        Intent i = new Intent(activity, ActivityReviewView.class);
        ReviewViewPacker.packView(activity, reviewView, i);
        activity.startActivityForResult(i, mRequestCode);
    }
}
