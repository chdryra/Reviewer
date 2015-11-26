/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer.View.Implementation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;

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

    @Override
    public int getRequestCode() {
        return mRequestCode;
    }

    @Override
    public Bundle getArguments() {
        return mArgs;
    }

    @Override
    public Activity getCommissioner(){
        return mCommissioner;
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
}
