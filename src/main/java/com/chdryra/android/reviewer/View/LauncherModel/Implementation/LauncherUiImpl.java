/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Knows how to launch a {@link LaunchableUi} depending on whether
 * it is a Dialog or Activity underneath.
 */
//TODO make Launchers plugins dependent of OS
public class LauncherUiImpl implements LauncherUi {
    private final Activity mCommissioner;
    private final int mRequestCode;
    private final String mTag;
    private final Bundle mArgs;
    private Class<? extends Activity> mReviewViewActivity;

    public LauncherUiImpl(Activity commissioner,
                          Class<? extends Activity> reviewViewActivity,
                          int requestCode, String tag, Bundle args) {
        mCommissioner = commissioner;
        mReviewViewActivity = reviewViewActivity;
        mRequestCode = requestCode;
        mTag = tag;
        mArgs = args;
    }

    @Override
    public void launch(DialogFragment launchableUI) {
        DialogShower.show(launchableUI, mCommissioner, mRequestCode, mTag, mArgs);
    }

    @Override
    public void launch(Class<? extends Activity> activityClass, String argsKey) {
        Intent i = new Intent(mCommissioner, activityClass);
        i.putExtra(argsKey, mArgs);
        mCommissioner.startActivityForResult(i, mRequestCode);
    }

    @Override
    public void launch(ReviewView<?> view) {
        Intent i = new Intent(mCommissioner, mReviewViewActivity);
        ReviewViewPacker.packView(mCommissioner, view, i);
        mCommissioner.startActivityForResult(i, mRequestCode);
    }
}
