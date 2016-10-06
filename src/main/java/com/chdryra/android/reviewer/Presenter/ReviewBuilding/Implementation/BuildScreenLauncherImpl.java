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

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenLauncherImpl implements BuildScreenLauncher {
    private RepositorySuite mRepository;
    private ReviewBuilderSuite mBuilder;
    private LaunchableConfig mUi;

    public BuildScreenLauncherImpl(LaunchableConfig ui, RepositorySuite repository, ReviewBuilderSuite builder) {
        mUi = ui;
        mRepository = repository;
        mBuilder = builder;
    }

    public void setUiLauncher(UiLauncher uiLauncher) {
        mUi.setLauncher(uiLauncher);
    }

    @Override
    public void launch(@Nullable ReviewId template) {
        mBuilder.discardReviewEditor();
        if (template != null) {
            mRepository.getReview(template, new Callback());
        } else {
            launchBuildUi(new Bundle());
        }
    }

    private void launchBuildUi(Bundle args) {
        mUi.launch(new UiLauncherArgs(mUi.getDefaultRequestCode()).setBundle(args));
    }

    private class Callback implements RepositoryCallback {
        @Override
        public void onRepositoryCallback(RepositoryResult result) {
            Bundle args = new Bundle();
            Review review = result.getReview();
            if (!result.isError() && review != null) {
                args.putString(TEMPLATE_ID, review.getReviewId().toString());
                mBuilder.packTemplate(review, args);
            }

            launchBuildUi(args);
        }
    }
}
