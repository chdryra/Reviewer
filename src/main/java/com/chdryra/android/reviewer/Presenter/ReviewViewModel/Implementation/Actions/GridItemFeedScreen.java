/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemFeedScreen extends GridItemLauncher<GvReview>
        implements AlertListener, NewReviewListener,
        ReviewsRepository.RepositoryCallback {
    private static final int SHARE_EDIT = RequestCodeGenerator.getCode("ShareEditReview");
    private static final int LAUNCH_BUILD_SCREEN = RequestCodeGenerator.getCode("BuildScreenTemplateReview");

    private LaunchableUiAlertable mShareEditUi;
    private LaunchableUi mBuildScreenUi;

    public GridItemFeedScreen(FactoryReviewView launchableFactory,
                              LaunchableUiAlertable shareEditUi,
                              LaunchableUi buildScreenUi) {
        super(launchableFactory);
        mShareEditUi = shareEditUi;
        mBuildScreenUi = buildScreenUi;
    }

    @Override
    public void onGridItemLongClick(GvReview item, int position, View v) {
        launchShareEdit(item);
    }

    private void launchShareEdit(GvReview item) {
        Bundle args = new Bundle();
        args.putParcelable(mShareEditUi.getLaunchTag(), item.getGvReviewId());
        launch(mShareEditUi, SHARE_EDIT, args);
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        getApp().getReview(template, this);
    }

    @Override
    public void onRepositoryCallback(RepositoryResult result) {
        Review review = result.getReview();
        if(result.isError() || review == null) return;

        Bundle args = new Bundle();
        args.putString(NewReviewListener.TEMPLATE_ID, review.getReviewId().toString());
        getApp().packReview(review, args);
        launch(mBuildScreenUi, LAUNCH_BUILD_SCREEN, args);
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
