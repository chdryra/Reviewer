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
 * UI Activity holding FragmentReviewLocationMap: mapping and editing a location.
 *
 * @see com.chdryra.android.reviewer.FragmentReviewLocationMap
 */
public class ActivityReviewLocationMap extends ActivitySingleFragment implements ReviewDataUI {

    private FragmentReviewLocationMap mFragment;

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

    @Override
    public void launch(ReviewDataUILauncher launcher) {
        launcher.launch(this);
    }
}
