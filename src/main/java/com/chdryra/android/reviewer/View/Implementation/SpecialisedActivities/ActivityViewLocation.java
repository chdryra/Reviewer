/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.SpecialisedActivities;

import android.app.Fragment;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityViewLocation extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityViewReviewLocation";
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
        return FragmentViewLocation.newInstance(mLocation);
    }

    private void getBundledLocation(LauncherUi launcher) {
        GvDataPacker<GvLocation> packer = new GvDataPacker<>();
        Bundle args = launcher.getArguments();
        if (args != null) {
            mLocation = packer.unpack(GvDataPacker.CurrentNewDatum.CURRENT, args);
        }
    }
}
