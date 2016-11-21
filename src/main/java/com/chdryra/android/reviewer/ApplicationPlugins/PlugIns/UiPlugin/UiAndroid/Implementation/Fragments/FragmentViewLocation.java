/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;

public class FragmentViewLocation extends FragmentMapLocation {
    private final static String LOCATION = TagKeyGenerator.getKey(FragmentViewLocation.class, "Location");

    private DataLocation mCurrent;

    public static Fragment newInstance(@Nullable GvLocation location) {
        return FactoryFragment.newFragment(FragmentViewLocation.class, LOCATION, location);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) mCurrent = args.getParcelable(LOCATION);
    }

    @Override
    void onMapReady() {
        zoomToLatLng(mCurrent.getLatLng());
        addMarker(mCurrent).showInfoWindow();
    }

    @Override
    void onGotoReviewSelected() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
        app.getUi().getLauncher().getReviewLauncher().launchAsList(mCurrent.getReviewId());
    }
}
