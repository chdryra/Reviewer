/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentEditUrlBrowser;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * UI Activity holding {@link FragmentEditUrlBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityEditUrlBrowser extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityEditUrlBrowser";
    private static final String KEY = "com.chdryra.android.reviewer.View.LauncherModel.Implementation." +
            "SpecialisedActivities.ActivityEditUrlBrowser.url";

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), KEY);
    }

    @Override
    protected Fragment createFragment() {
        GvUrl bundledUrl = getBundledUrl();
        return FragmentEditUrlBrowser.newInstance(bundledUrl != null ? bundledUrl : new GvUrl());
    }

    @Nullable
    protected GvUrl getBundledUrl() {
        ParcelablePacker<GvUrl> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(KEY);
        return args != null? packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : new GvUrl();
    }
}
