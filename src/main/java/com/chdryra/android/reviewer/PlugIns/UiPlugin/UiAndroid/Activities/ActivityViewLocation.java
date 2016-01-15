/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities;

import android.app.Fragment;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Fragments.FragmentEditLocationMap;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Fragments.FragmentViewLocation;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityViewLocation extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityViewReviewLocation";
    private static final String KEY = "com.chdryra.android.reviewer.View.LauncherModel.Implementation.Activities.SpecialisedActivities.ActivityViewLocation.location";

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
        return FragmentViewLocation.newInstance(getBundledLocation());
    }

    private GvLocation getBundledLocation() {
        GvDataPacker<GvLocation> packer = new GvDataPacker<>();
        Bundle args = getIntent().getBundleExtra(KEY);
        return args != null ?
                packer.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args) : new GvLocation();
    }
}