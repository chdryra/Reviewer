/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.content.Intent;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.PresenterFeed;
import com.chdryra.android.startouch.Social.Implementation.PublishResults;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView implements
        PresenterFeed.PresenterListener{
    private static final String TAG = TagKeyGenerator.getTag(ActivityFeed.class);

    private PresenterFeed mPresenter;

    @Override
    protected ReviewView createReviewView() {
        mPresenter = new PresenterFeed.Builder().build(getApp(), this);
        mPresenter.attach();
        return mPresenter.getView();
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {
        //TODO show publishing status
    }
}
