/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Activities;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.corelibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentLogin;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

public class ActivityLogin extends ActivitySingleFragment implements LaunchableUi {
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
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), KEY);
        AppInstanceAndroid.getInstance(this).getUi().getCurrentScreen().close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) mFragment = ((FragmentLogin) getFragment());
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(RETAIN_VIEW, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        if (mFragment == null) mFragment = FragmentLogin.newInstance();
        return mFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }
}
