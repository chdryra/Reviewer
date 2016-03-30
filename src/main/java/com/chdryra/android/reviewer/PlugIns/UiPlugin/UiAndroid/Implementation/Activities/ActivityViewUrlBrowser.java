/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentEditUrlBrowser;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentOAuthUrlBrowser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * UI Activity holding {@link FragmentEditUrlBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityViewUrlBrowser extends ActivityEditUrlBrowser {
    private static final String TAG = "ActivityViewUrlBrowser";
    private static final String KEY = "com.chdryra.android.reviewer.View.LauncherModel.Implementation." +
            "SpecialisedActivities.ActivityViewUrlBrowser.url";

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
        return FragmentOAuthUrlBrowser.newInstance(getBundledRequest());
    }

    @Nullable
    protected OAuthRequest getBundledRequest() {
        ParcelablePacker<OAuthRequest> packer = new ParcelablePacker<>();
        return packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, getIntent());
    }
}
