/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentEditLocationMap;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentViewLocation;
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
        ParcelablePacker<GvLocation> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(KEY);
        return args != null ?
                packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : new GvLocation();
    }
}
