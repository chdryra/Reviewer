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
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView
        implements DialogAlertFragment.DialogAlertListener{

    //Overridden
    @Override
    protected ReviewView createView() {
        return FeedScreen.newScreen(this, Administrator.get(this).getReviewsRepository());
    }

    //Overridden
    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
        GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList
                .GvReviewOverview) datum;
        Administrator.get(this).deleteFromAuthorsFeed(review.getId());
    }
}
