/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentEditLocationMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentViewLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityViewLocation extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = TagKeyGenerator.getTag(ActivityViewLocation.class);
    private static final String LOCATION = TagKeyGenerator.getKey(ActivityViewLocation.class,
            "Location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), LOCATION);
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        return FragmentViewLocation.newInstance(getBundledLocation());
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
        AppInstanceAndroid.getInstance(this).onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    @Nullable
    private GvLocation getBundledLocation() {
        ParcelablePacker<GvLocation> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(LOCATION);
        return args != null ?
                packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : new GvLocation();
    }
}
