/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewPublish;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityPublishReview extends ActivityReviewView
        implements PlatformAuthoriser, PublishAction.PublishCallback {
    private static final int SOCIAL = R.string.activity_title_share;
    private static final int PUBLISHING = R.string.publishing;
    private static final int PROBLEM_PUBLISHING = R.string.problem_publishing;

    private ApplicationInstance mApp;
    private PresenterReviewPublish mPresenter;
    private LoginUi mAuthUi;

    @Override
    protected ReviewView createReviewView() {
        mApp = ApplicationInstance.getInstance(this);

        PresenterReviewPublish.Builder builder = new PresenterReviewPublish.Builder(mApp);
        mPresenter = builder.build(mApp.getReviewBuilderAdapter(), this, this, getString(SOCIAL));

        return mPresenter.getView();
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        LaunchableUiLauncher launcher = mApp.getUiLauncher();
        mAuthUi = platform.getLoginUi(this, new ActivitySocialAuthUi(), listener);
        mAuthUi.launchUi(launcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAuthUi.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onQueuedToPublish(ReviewId id, CallbackMessage message) {
        Toast.makeText(this, getString(PUBLISHING), Toast.LENGTH_LONG).show();
        mPresenter.onQueuedToPublish(this);
    }

    @Override
    public void onFailedToQueue(@Nullable Review review, @Nullable ReviewId id, CallbackMessage message) {
        Toast.makeText(this, getString(PROBLEM_PUBLISHING) + ": " + message.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}
