/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityReviewsList extends ActivityReviewView implements NewReviewListener{
    private static final String TAG = TagKeyGenerator.getTag(ActivityReviewsList.class);

    private ApplicationInstance mApp;
    private PresenterReviewsList mPresenter;

    @Override
    protected ReviewView createReviewView() {
        ReviewsListView view = (ReviewsListView) super.createReviewView();
        mApp = ApplicationInstance.getInstance(this);
        mPresenter = new PresenterReviewsList.Builder(mApp).build(view);

        return mPresenter.getView();
    }


    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    protected void onResume() {
        mApp.discardReviewBuilderAdapter();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mPresenter.onNewReviewUsingTemplate(template);
    }
}
