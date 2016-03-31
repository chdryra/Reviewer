/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryMessage;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.mygenerallibrary.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemFeedScreen extends GridItemLauncher<GvReviewOverview>
        implements DialogAlertFragment.DialogAlertListener, NewReviewListener,
        RepositoryCallback {
    private static final int SHARE_EDIT = RequestCodeGenerator.getCode("ShareEditReview");
    private static final int LAUNCH_BUILD_SCREEN = RequestCodeGenerator.getCode("BuildScreenTemplateReview");

    private LaunchableUiAlertable mShareEditUi;
    private LaunchableUi mBuildScreenUi;

    public GridItemFeedScreen(FactoryReviewViewLaunchable launchableFactory,
                              LaunchableUiLauncher launcher,
                              LaunchableUiAlertable shareEditUi,
                              LaunchableUi buildScreenUi) {
        super(launchableFactory, launcher);
        mShareEditUi = shareEditUi;
        mBuildScreenUi = buildScreenUi;
    }

    @Override
    public void onGridItemLongClick(GvReviewOverview item, int position, View v) {
        launchShareEdit(item);
    }

    private void launchShareEdit(GvReviewOverview item) {
        Bundle args = new Bundle();
        args.putParcelable(mShareEditUi.getLaunchTag(), item.getGvReviewId());
        launch(mShareEditUi, SHARE_EDIT, args);
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        ApplicationInstance app = ApplicationInstance.getInstance(getActivity());
        app.getReview(template, this);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, RepositoryMessage result) {
        if(review == null) return;

        ApplicationInstance app = ApplicationInstance.getInstance(getActivity());
        Bundle args = new Bundle();
        args.putString(NewReviewListener.TEMPLATE_ID, review.getReviewId().toString());
        app.cacheReview(review, args);
        launch(mBuildScreenUi, LAUNCH_BUILD_SCREEN, args);
    }

    @Override
    public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryMessage result) {

    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mShareEditUi.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mShareEditUi.onAlertPositive(requestCode, args);
    }
}
