/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.NetworkSuite;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;
import com.chdryra.android.startouch.Social.Interfaces.LoginUi;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewPublish implements ActivityResultListener, PlatformAuthoriser, PublishAction.PublishCallback {
    private final UiSuite mUi;
    private final NetworkSuite mNetwork;
    private final LaunchableUi mAuthLaunchable;
    private final EditorSuite mEditor;

    private ReviewView<?> mView;
    private LoginUi mAuthUi;

    private PresenterReviewPublish(UiSuite ui,
                                   NetworkSuite network,
                                   EditorSuite editor,
                                   LaunchableUi authLaunchable) {
        mUi = ui;
        mNetwork = network;
        mEditor = editor;
        mAuthLaunchable = authLaunchable;
    }

    private void setView(ReviewView<?> view) {
        mView = view;
    }

    public ReviewView<?> getView() {
        return mView;
    }

    @Override
    public void onQueuedToPublish(ReviewId id, CallbackMessage message) {
        showToast(Strings.Toasts.PUBLISHING);
        mEditor.discardEditor(false, null);
        mUi.returnToFeedScreen();
    }

    @Override
    public void onFailedToQueue(@Nullable Review review, @Nullable ReviewId id, CallbackMessage
            message) {
        showToast(Strings.Toasts.PROBLEM_PUBLISHING + ": " + message.getMessage());
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, Callback callback) {
        if(mNetwork.isOnline(mUi.getCurrentScreen())) {
            mAuthUi = platform.getLoginUi(mAuthLaunchable, callback);
            mAuthUi.launchUi(mUi.getLauncher());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mAuthUi != null) mAuthUi.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String publishing) {
        mUi.getCurrentScreen().showToast(publishing);
    }

    public static class Builder {
        public PresenterReviewPublish build(ApplicationInstance app, LaunchableUi authLaunchable) {
            UiSuite ui = app.getUi();

            PresenterReviewPublish presenter = new PresenterReviewPublish(ui,
                    app.getNetwork(), app.getEditor(), authLaunchable);

            ReviewEditor<?> editor = app.getEditor().getEditor();
            SocialPlatformList platforms = app.getSocial().getSocialPlatforms();
            ReviewPublisher publisher = app.getRepository().getReviewPublisher();

            ReviewView<?> reviewView = ui.newPublishView(editor, publisher, platforms,
                    presenter, presenter);

            presenter.setView(reviewView);

            return presenter;
        }
    }
}
