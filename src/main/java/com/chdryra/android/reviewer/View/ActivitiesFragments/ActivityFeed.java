/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView
        implements DialogAlertFragment.DialogAlertListener{

    private FeedScreen mScreen;

    //Overridden
    @Override
    protected ReviewView createReviewView() {
        mScreen = new FeedScreen(this, Administrator.get(this).getReviewsRepository());
        return mScreen.getReviewView();
    }

    //Overridden
    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mScreen.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mScreen.onAlertPositive(requestCode, args);
    }
}
