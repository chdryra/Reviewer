/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityReviewView extends ActivitySingleFragment implements LaunchableUi,
        OptionSelectListener {
    private static final String TAG = TagKeyGenerator.getTag(ActivityReviewView.class);
    private static final String FRAGMENT = "Fragment";

    @Nullable
    public ReviewView<?> createReviewView() {
        return getApp().unpackView(getIntent());
    }

    protected AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, FRAGMENT, getFragment());
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return getFragmentManager().getFragment(savedInstanceState, FRAGMENT);
        } else {
            return new FragmentReviewView();
        }
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), getLaunchTag());
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        FragmentReviewView fragment = (FragmentReviewView) getFragment();
        return fragment.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        FragmentReviewView fragment = (FragmentReviewView) getFragment();
        return fragment.onOptionsCancelled(requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        getApp().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
