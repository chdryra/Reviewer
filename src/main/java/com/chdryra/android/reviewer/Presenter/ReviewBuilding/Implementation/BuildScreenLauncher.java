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
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenLauncher implements RepositoryCallback {
    private static final int LAUNCH_BUILD_SCREEN = RequestCodeGenerator.getCode("BuildScreenNewReview");
    private ApplicationInstance mApp;
    private ReviewId mTemplate;

    public void launch(ApplicationInstance app, @Nullable ReviewId template) {
        mTemplate = template;
        launch(app);
    }

    public void launch(ApplicationInstance app) {
        mApp = app;
        mApp.discardReviewBuilderAdapter();
        if (mTemplate != null) {
            mApp.getReview(mTemplate, this);
        } else {
            launchBuildUi(new Bundle());
        }
    }

    private void launchBuildUi(Bundle args) {
        LaunchableUi ui = mApp.getConfigUi().getBuildReview().getLaunchable();
        mApp.getUiLauncher().launch(ui, LAUNCH_BUILD_SCREEN, args);
    }

    @Override
    public void onRepositoryCallback(RepositoryResult result) {
        Bundle args = new Bundle();
        Review review = result.getReview();
        if (!result.isError() && review != null) {
            args.putString(NewReviewListener.TEMPLATE_ID, mTemplate.toString());
            mApp.packReview(review, args);
        }

        launchBuildUi(args);
    }
}
