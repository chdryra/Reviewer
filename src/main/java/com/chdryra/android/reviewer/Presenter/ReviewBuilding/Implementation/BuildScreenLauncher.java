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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
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
public class BuildScreenLauncher {
    private static final int LAUNCH_BUILD_SCREEN
            = RequestCodeGenerator.getCode("BuildScreenNewReview");

    public void launch(ApplicationInstance app, @Nullable ReviewId template) {
        app.discardReviewBuilderAdapter();
        if (template != null) {
            app.getReview(template, new Callback(app));
        } else {
            launchBuildUi(app, new Bundle());
        }
    }

    private void launchBuildUi(ApplicationInstance app, Bundle args) {
        LaunchableUi ui = app.getConfigUi().getBuildReview().getLaunchable();
        app.getUiLauncher().launch(ui, LAUNCH_BUILD_SCREEN, args);
    }

    private class Callback implements RepositoryCallback {
        private ApplicationInstance mApp;

        public Callback(ApplicationInstance app) {
            mApp = app;
        }

        @Override
        public void onRepositoryCallback(RepositoryResult result) {
            Bundle args = new Bundle();
            Review review = result.getReview();
            if (!result.isError() && review != null) {
                args.putString(NewReviewListener.TEMPLATE_ID, review.getReviewId().toString());
                mApp.packReview(review, args);
            }

            launchBuildUi(mApp, args);
        }
    }
}
