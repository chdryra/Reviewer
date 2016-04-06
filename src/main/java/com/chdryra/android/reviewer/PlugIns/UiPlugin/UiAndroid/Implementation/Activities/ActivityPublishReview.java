/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.content.Intent;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewPublish;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityPublishReview extends ActivityReviewView implements PlatformAuthoriser {
    private static final int SOCIAL = R.string.activity_title_share;
    private LoginUi mAuthUi;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        PublishActionAndroid action = new PublishActionAndroid(app, this, ActivityUsersFeed.class);

        ReviewBuilderAdapter<? extends GvDataList<?>> review = app.getReviewBuilderAdapter();
        PresenterReviewPublish presenter = new PresenterReviewPublish.Builder(app)
                .build(review, this, action, getString(SOCIAL));

        return presenter.getView();
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        LaunchableUiLauncher launcher = ApplicationInstance.getInstance(this).getUiLauncher();
        mAuthUi = platform.getLoginUi(this, new ActivitySocialAuthUi(), listener);
        mAuthUi.launchUi(launcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAuthUi.onActivityResult(requestCode, resultCode, data);
    }
}
