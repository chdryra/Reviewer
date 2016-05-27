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
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
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
    private static final String TAG = TagKeyGenerator.getTag(ActivityEditUrlBrowser.class);
    private static final String URL = TagKeyGenerator.getKey(ActivityEditUrlBrowser.class, "Url");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationInstance.setActivity(this);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), URL);
    }

    @Override
    protected Fragment createFragment() {
        GvUrl bundledUrl = getBundledUrl();
        return FragmentEditUrlBrowser.newInstance(bundledUrl != null ? bundledUrl : new GvUrl());
    }

    @Nullable
    protected GvUrl getBundledUrl() {
        ParcelablePacker<GvUrl> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(URL);
        return args != null? packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : new GvUrl();
    }
}
