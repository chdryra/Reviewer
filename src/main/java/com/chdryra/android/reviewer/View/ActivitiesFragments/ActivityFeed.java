/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.Fragment;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView {

    @Override
    protected Fragment createFragment() {
        Administrator.get(this).packView(FeedScreen.newScreen(this), getIntent());
        return new FragmentReviewView();
    }

}
