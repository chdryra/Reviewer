/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.content.Intent;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterFeed;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView implements
        PresenterFeed.PresenterListener{
    private static final String TAG = TagKeyGenerator.getTag(ActivityFeed.class);

    private PresenterFeed mPresenter;

    @Override
    protected ReviewView createReviewView() {
        mPresenter = new PresenterFeed.Builder().build(AppInstanceAndroid.getInstance(this), this);
        mPresenter.attach();
        return mPresenter.getView();
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    protected void onResume() {
        AppInstanceAndroid.getInstance(this).getReviewEditor().discardEditor();
        super.onResume();
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

    }
}
