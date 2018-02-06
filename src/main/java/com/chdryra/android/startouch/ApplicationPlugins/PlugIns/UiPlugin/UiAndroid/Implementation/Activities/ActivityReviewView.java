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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentReviewView;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewViewFragmentLayout;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewEditFragmentLayout;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewViewLayout;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
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

    private ReviewView<?> mView;

    public ReviewView<?> getReviewView() {
        return mView;
    }

    public ReviewViewLayout getReviewLayout() {
        return createReviewViewLayout(mView.getParams().getViewType());
    }

    protected AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(this);
    }

    protected ReviewViewLayout createReviewViewLayout(ReviewViewParams.ViewType viewType) {
        return viewType == ReviewViewParams.ViewType.VIEW ? new ReviewViewFragmentLayout() : new
                ReviewEditFragmentLayout();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getApp().retainView(mView, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        //Get retained to remove from memory but prefer new view over retained view
        ReviewView<?> retained = null;
        if (savedInstanceState != null) retained = getApp().getRetainedView(savedInstanceState);

        mView = createReviewView();
        if (mView == null) mView = retained;

        return new FragmentReviewView();
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
        boolean consumed = mView.onOptionSelected(requestCode, option);
        if (!consumed) {
            FragmentReviewView fragment = (FragmentReviewView) getFragment();
            consumed = fragment.onOptionSelected(requestCode, option);
        }

        return consumed;
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        boolean consumed = mView.onOptionsCancelled(requestCode);
        if (!consumed) {
            FragmentReviewView fragment = (FragmentReviewView) getFragment();
            consumed = fragment.onOptionsCancelled(requestCode);
        }

        return consumed;
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

    @Nullable
    ReviewView<?> createReviewView() {
        return getApp().unpackView(getIntent());
    }
}
