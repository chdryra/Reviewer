/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.SpecialisedActivities;

import android.app.Fragment;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

/**
 * UI Activity holding {@link FragmentEditUrlBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityEditUrlBrowser extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityEditUrlMap";
    private static final String KEY = "com.chdryra.android.reviewer.View.Implementation." +
            "SpecialisedActivities.ActivityEditUrlBrowser.location";
    private GvUrl mUrl;

    //Overridden
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
        return FragmentEditUrlBrowser.newInstance(getBundledUrl());
    }

    private GvUrl getBundledUrl() {
        GvDataPacker<GvUrl> packer = new GvDataPacker<>();
        Bundle args = getIntent().getBundleExtra(KEY);
        return args != null? packer.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args) : new GvUrl();
    }
}
