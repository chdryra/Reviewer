/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;

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
        onMarkerClick(addMarker(mCurrent));
        zoomToLatLng(mCurrent.getLatLng());
    }

    @Override
    void onGotoReviewSelected() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
        app.getUi().getLauncher().getReviewLauncher().launchAsList(mCurrent.getReviewId());
    }
}
