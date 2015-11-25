/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.LaunchableActivities;

import android.app.Fragment;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;

/**
 * UI Activity holding {@link FragmentEditUrlBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityEditUrlBrowser extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityEditUrlMap";
    private GvUrl mUrl;

    //Overridden
    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        getBundledLocation(launcher);
        launcher.launch(this);
    }

    @Override
    protected Fragment createFragment() {
        return FragmentEditUrlBrowser.newInstance(mUrl);
    }

    private void getBundledLocation(LauncherUi launcher) {
        GvDataPacker<GvUrl> packer = new GvDataPacker<>();
        Bundle args = launcher.getArguments();
        if (args != null) mUrl = packer.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args);
    }
}
