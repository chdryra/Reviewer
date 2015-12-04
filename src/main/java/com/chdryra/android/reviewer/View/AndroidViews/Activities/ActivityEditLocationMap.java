/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.AndroidViews.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.View.AndroidViews.Fragments.FragmentEditLocationMap;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityEditLocationMap extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityEditLocationMap";
    private static final String KEY = "com.chdryra.android.reviewer.View.Implementation." +
            "SpecialisedActivities.ActivityEditLocationMap.location";

    private FragmentEditLocationMap mFragment;

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
        mFragment = FragmentEditLocationMap.newInstance(getBundledLocation());
        return mFragment;
    }

    //How the search widget passes back search data to the host activity
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        mFragment.handleSearch();
    }

    private GvLocation getBundledLocation() {
        GvDataPacker<GvLocation> packer = new GvDataPacker<>();
        Bundle args = getIntent().getBundleExtra(KEY);
        return args != null ?
                packer.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args) : new GvLocation();
    }
}
