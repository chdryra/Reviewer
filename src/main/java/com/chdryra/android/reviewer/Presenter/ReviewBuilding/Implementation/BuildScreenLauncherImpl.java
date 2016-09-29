/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.ReviewPacker;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenLauncherImpl implements BuildScreenLauncher {
    private static final int LAUNCH_BUILD_SCREEN
            = RequestCodeGenerator.getCode("BuildScreenLauncher");

    private PresenterContext mAppContext;
    private ReviewPacker mPacker;
    private LaunchableConfig mBuildReviewConfig;
    private UiLauncher mUiLauncher;

    public BuildScreenLauncherImpl(PresenterContext appContext, ReviewPacker packer, LaunchableConfig buildReviewConfig) {
        mAppContext = appContext;
        mPacker = packer;
        mBuildReviewConfig = buildReviewConfig;
    }

    public void setUiLauncher(UiLauncher uiLauncher) {
        mUiLauncher = uiLauncher;
    }

    @Override
    public void launch(@Nullable ReviewId template) {
        mAppContext.discardReviewEditor();
        if (template != null) {
            mAppContext.getReview(template, new Callback());
        } else {
            launchBuildUi(new Bundle());
        }
    }

    private void launchBuildUi(Bundle args) {
        mUiLauncher.launch(mBuildReviewConfig.getLaunchable(), LAUNCH_BUILD_SCREEN, args);
    }

    private class Callback implements RepositoryCallback {
        @Override
        public void onRepositoryCallback(RepositoryResult result) {
            Bundle args = new Bundle();
            Review review = result.getReview();
            if (!result.isError() && review != null) {
                args.putString(TEMPLATE_ID, review.getReviewId().toString());
                mPacker.packReview(review, args);
            }

            launchBuildUi(args);
        }
    }
}
