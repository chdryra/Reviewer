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
import com.chdryra.android.reviewer.View.Screens.AuthorFeedScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView {

    //Overridden
    @Override
    protected Fragment createFragment() {
        ReviewView feedScreen = AuthorFeedScreen.newScreen(this);
        Administrator.get(this).packView(feedScreen, getIntent());

        return new FragmentReviewView();
    }

}
