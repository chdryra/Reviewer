/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFeedScreen {
    private FeedScreen mFeedScreen;
    private ReviewView mView;
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryReviewViewLaunchable mLaunchableFactory;
    private LaunchableUiLauncher mLauncher;
    private LaunchableUiAlertable mShareEditUi;
    private LaunchableUi mReviewBuildScreenUi;
    private FactoryReviews mReviewFactory;

    public FactoryFeedScreen(FactoryReviewViewAdapter adapterFactory,
                             FactoryReviewViewLaunchable launchableFactory,
                             LaunchableUiLauncher launcher,
                             LaunchableUiAlertable shareEditUi,
                             LaunchableUi reviewBuildScreenUi,
                             FactoryReviews reviewFactory) {
        mAdapterFactory = adapterFactory;
        mLaunchableFactory = launchableFactory;
        mLauncher = launcher;
        mReviewFactory = reviewFactory;
        mReviewBuildScreenUi = reviewBuildScreenUi;
        mShareEditUi = shareEditUi;
    }

    public FeedScreen getFeedScreen() {
        return mFeedScreen;
    }

    public ReviewView getView() {
        return mView;
    }

    public void buildScreen(ReviewsFeed feed) {
        String title = feed.getAuthor().getName() + "'s feed";
        mFeedScreen = new FeedScreen(feed, title, mReviewFactory);
        mView = mFeedScreen.createView(mLaunchableFactory, mAdapterFactory, getActions());
    }

    @NonNull
    private FeedScreen.Actions getActions() {
        GridItemFeedScreen gi = new GridItemFeedScreen(mLaunchableFactory, mLauncher, mShareEditUi,
                mReviewBuildScreenUi);
        SubjectAction<GvReviewOverview> sa = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverview> rb
                = new RatingBarExpandGrid<>(mLaunchableFactory, mLauncher);
        BannerButtonAction<GvReviewOverview> bba = new BannerButtonActionNone<>();
        MenuFeedScreen ma = new MenuFeedScreen(mLauncher, mReviewBuildScreenUi);

        return new FeedScreen.Actions(sa, rb, bba, gi, ma);
    }
}
