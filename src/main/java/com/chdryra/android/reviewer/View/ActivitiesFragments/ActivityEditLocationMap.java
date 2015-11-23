/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityEditLocationMap extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityEditLocationMap";
    private FragmentEditLocationMap mFragment;
    private GvLocation mLocation;

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
        mFragment = FragmentEditLocationMap.newInstance(mLocation);
        return mFragment;
    }

    //How the search widget passes back search data to the host activity
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        mFragment.handleSearch();
    }

    private void getBundledLocation(LauncherUi launcher) {
        GvDataPacker<GvLocation> packer = new GvDataPacker<>();
        Bundle args = launcher.getArguments();
        if (args != null) {
            mLocation = packer.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args);
        }
    }
}
