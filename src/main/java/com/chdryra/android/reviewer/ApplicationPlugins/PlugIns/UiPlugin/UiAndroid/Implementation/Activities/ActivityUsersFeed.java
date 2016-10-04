/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;


import android.content.Intent;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterUsersFeed;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityUsersFeed extends ActivityReviewsList implements
        PresenterUsersFeed.PresenterListener {

    @Override
    protected PresenterReviewsList newPresenter() {
        return new PresenterUsersFeed.Builder().build(AppInstanceAndroid.getInstance(this), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {

    }
}
