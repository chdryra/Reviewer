/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentEditLocationMap;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityEditLocationMap extends ActivitySingleFragment implements LaunchableUi,
        FragmentEditLocationMap.LocationEditListener {
    private static final String TAG = TagKeyGenerator.getTag(ActivityEditLocationMap.class);
    private static final String LOCATION
            = TagKeyGenerator.getKey(ActivityEditLocationMap.class, "Location");

    private FragmentEditLocationMap mFragment;
    private ParcelablePacker<GvLocation> mDataPacker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidAppInstance.setActivity(this);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), LOCATION);
    }

    @Override
    protected Fragment createFragment() {
        mDataPacker = new ParcelablePacker<>();
        mFragment = FragmentEditLocationMap.newInstance(getBundledLocation());
        return mFragment;
    }

    //How the search widget passes back search data to the host activity
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        mFragment.handleSearch();
    }

    @Nullable
    private GvLocation getBundledLocation() {
        Bundle args = getIntent().getBundleExtra(LOCATION);
        return args != null ?
                mDataPacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : new GvLocation();
    }

    @Override
    public void onDelete(GvLocation location, Intent returnResult) {
        mDataPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, location, returnResult);
    }

    @Override
    public void onDone(GvLocation currentLocation, GvLocation newLocation, Intent returnResult) {
        mDataPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, currentLocation, returnResult);
        mDataPacker.packItem(ParcelablePacker.CurrentNewDatum.NEW, newLocation , returnResult);
    }
}
