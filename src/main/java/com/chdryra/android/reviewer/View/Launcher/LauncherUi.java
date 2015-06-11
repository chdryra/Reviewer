/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer.View.Launcher;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Adapter.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Knows how to launch a {@link LaunchableUi} depending on whether
 * it is a Dialog or Activity underneath.
 */
public class LauncherUi {
    private static final String LAUNCHER_ARGS = "com.chdryra.android.review.args_key";
    private final Fragment mCommissioner;
    private final int      mRequestCode;
    private final String   mTag;
    private final Bundle   mArgs;

    private LauncherUi(Fragment commissioner, int requestCode, String tag, Bundle args) {
        mCommissioner = commissioner;
        mRequestCode = requestCode;
        mTag = tag;
        mArgs = args;
    }

    public static void launch(LaunchableUi ui, Fragment commissioner, int requestCode, String tag,
            Bundle args) {
        if (ui == null) return;
        ui.launch(new LauncherUi(commissioner, requestCode, tag, args));
    }

    public static Bundle getArgsForActivity(Activity launchableUI) {
        return launchableUI.getIntent().getBundleExtra(LAUNCHER_ARGS);
    }

    public void launch(DialogFragment launchableUI) {
        DialogShower.show(launchableUI, mCommissioner, mRequestCode, mTag, mArgs);
    }

    public void launch(Activity launchableUI) {
        Intent i = new Intent(mCommissioner.getActivity(), launchableUI.getClass());
        i.putExtra(LAUNCHER_ARGS, mArgs);
        mCommissioner.startActivityForResult(i, mRequestCode);
    }

    public void launch(ReviewView reviewView) {
        Activity activity = mCommissioner.getActivity();
        Intent i = new Intent(activity, ActivityReviewView.class);
        Administrator.get(activity).packView(reviewView, i);
        activity.startActivityForResult(i, mRequestCode);
    }
}
