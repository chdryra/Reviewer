/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Knows how to launch a {@link com.chdryra.android.reviewer.LaunchableUI} depending on whether
 * it is a Dialog or Activity underneath.
 */
class LauncherUI {
    private static final String LAUNCHER_ARGS = "com.chdryra.android.review.args_key";
    private final Fragment mCommissioner;
    private final int      mRequestCode;
    private final String   mTag;
    private final Bundle   mArgs;

    private LauncherUI(Fragment commissioner, int requestCode, String tag, Bundle args) {
        mCommissioner = commissioner;
        mRequestCode = requestCode;
        mTag = tag;
        mArgs = args;
    }

    static void launch(LaunchableUI ui, Fragment commissioner, int requestCode, String tag,
                       Bundle args) {
        ui.launch(new LauncherUI(commissioner, requestCode, tag,
                                           args));
    }

    static Bundle getArgsForActivity(Activity launchableUI) {
        return launchableUI.getIntent().getBundleExtra(LAUNCHER_ARGS);
    }

    void launch(DialogFragment launchableUI) {
        DialogShower.show(launchableUI, mCommissioner, mRequestCode, mTag, mArgs);
    }

    void launch(Activity launchableUI) {
        Intent i = new Intent(mCommissioner.getActivity(), launchableUI.getClass());
        i.putExtra(LAUNCHER_ARGS, mArgs);
        mCommissioner.startActivityForResult(i, mRequestCode);
    }
}
