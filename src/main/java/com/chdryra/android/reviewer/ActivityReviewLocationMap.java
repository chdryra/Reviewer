/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;


/**
 * UI Activity holding {@link FragmentReviewLocationMap}: mapping and editing a location.
 */
public class ActivityReviewLocationMap extends ActivitySingleFragment implements LaunchableUI {

    private FragmentReviewLocationMap mFragment;

    @Override
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    @Override
    protected Fragment createFragment() {
        mFragment = new FragmentReviewLocationMap();
        return mFragment;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        mFragment.handleSearch();
    }
}
