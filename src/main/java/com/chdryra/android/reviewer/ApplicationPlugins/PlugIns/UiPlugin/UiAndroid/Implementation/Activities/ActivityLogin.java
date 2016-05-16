/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentLogin;
import com.chdryra.android.reviewer.Application.ApplicationLaunch;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

public class ActivityLogin extends ActivitySingleFragment implements LaunchableUiAlertable {
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
        Activity commissioner = launcher.getCommissioner();
        launcher.launch(new Intent(commissioner, ActivityLogin.class), KEY);
        commissioner.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) mFragment = ((FragmentLogin) getFragment());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(RETAIN_VIEW, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createFragment() {
        ApplicationLaunch.launchIfNecessary(this, ApplicationLaunch.LaunchState.TEST);
        if(mFragment == null) mFragment = FragmentLogin.newInstance();
        return mFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mFragment != null) {
            mFragment.reobserveUser();
        }
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
}
