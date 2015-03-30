/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View;

import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityEditLocationMap extends ActivitySingleFragment implements LaunchableUi {

    private FragmentEditLocationMap mFragment;

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    protected Fragment createFragment() {
        mFragment = new FragmentEditLocationMap();
        return mFragment;
    }

    //How the search widget passes back search data to the host activity
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        mFragment.handleSearch();
    }
}
