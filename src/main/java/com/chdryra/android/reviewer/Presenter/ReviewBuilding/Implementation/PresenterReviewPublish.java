/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewPublish implements ActivityResultListener, PlatformAuthoriser, PublishAction.PublishCallback {
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");

    private final CurrentScreen mScreen;
    private final LaunchableUi mFeed;
    private final LaunchableUi mAuthLaunchable;
    private final UiLauncher mLauncher;

    private ReviewView<?> mView;
    private LoginUi mAuthUi;


    private PresenterReviewPublish(CurrentScreen screen,
                                   LaunchableUi feed,
                                   LaunchableUi authLaunchable,
                                   UiLauncher launcher) {

        mScreen = screen;
        mFeed = feed;
        mAuthLaunchable = authLaunchable;
        mLauncher = launcher;
    }

    public void setView(ReviewView<?> view) {
        mView = view;
    }

    public ReviewView<?> getView() {
        return mView;
    }

    @Override
    public void onQueuedToPublish(ReviewId id, CallbackMessage message) {
        showToast(Strings.Toasts.PUBLISHING);
        mLauncher.launchAndClearBackStack(mFeed, FEED);
        mScreen.close();
    }

    @Override
    public void onFailedToQueue(@Nullable Review review, @Nullable ReviewId id, CallbackMessage
            message) {
        showToast(Strings.Toasts.PROBLEM_PUBLISHING + ": " + message.getMessage());
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        mAuthUi = platform.getLoginUi(mAuthLaunchable, listener);
        mAuthUi.launchUi(mLauncher);    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mAuthUi != null) mAuthUi.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String publishing) {
        mScreen.showToast(publishing);
    }

    public static class Builder {
        public PresenterReviewPublish build(ApplicationInstance app, LaunchableUi authLaunchable) {
            UiSuite ui = app.getUi();

            PresenterReviewPublish presenter = new PresenterReviewPublish(ui.getCurrentScreen(),
                    ui.getConfig().getUsersFeed().getLaunchable(), authLaunchable, ui.getLauncher());

            ReviewEditor<?> editor = app.getReviewBuilder().getReviewEditor();
            SocialPlatformList platforms = app.getSocial().getSocialPlatformList();
            ReviewPublisher publisher = app.getRepository().getReviewPublisher();

            ReviewView<?> reviewView = ui.newPublishView(editor, publisher, platforms, presenter, presenter);

            presenter.setView(reviewView);

            return presenter;
        }
    }
}
