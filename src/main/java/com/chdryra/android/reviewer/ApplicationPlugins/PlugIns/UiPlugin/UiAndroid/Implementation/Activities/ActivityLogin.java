/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentLogin;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

public class ActivityLogin extends ActivitySingleFragment implements LaunchableUi, AlertListener {
    private static final String TAG = TagKeyGenerator.getTag(ActivityLogin.class);
    private static final String KEY = TagKeyGenerator.getKey(ActivityLogin.class, "Key");
    private static final String RETAIN_VIEW
            = TagKeyGenerator.getKey(ActivityLogin.class, "RetainView");

    private FragmentLogin mFragment;

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), KEY);
        launcher.getCommissioner().finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) mFragment = ((FragmentLogin) getFragment());
        AndroidAppInstance.setActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(RETAIN_VIEW, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createFragment() {
        if(mFragment == null) mFragment = FragmentLogin.newInstance();
        return mFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mFragment.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mFragment.onAlertPositive(requestCode, args);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFragment.closeDialogs(); //stop "Leaked window" error.
    }

    @Override
    protected void onStop() {
        mFragment.closeDialogs(); //stop "Leaked window" error.
        super.onStop();
    }
}
