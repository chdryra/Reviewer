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

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.startouch.Application.Implementation.Settings;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.ReviewPack;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewBuild implements ActivityResultListener, PublishAction.PublishCallback{

    private final CurrentScreen mScreen;

    private EditorSuite mSuite;
    private ReviewEditor<?> mEditor;

    private PresenterReviewBuild(CurrentScreen screen) {
        mScreen = screen;
    }

    private void setSuite(EditorSuite suite) {
        mSuite = suite;
        mEditor = suite.getEditor();
    }

    public ReviewEditor getEditor() {
        return mEditor;
    }

    private void showToast(String publishing) {
        mScreen.showToast(publishing);
    }

    @Override
    public void onQueuedToPublish(ReviewId id, CallbackMessage message) {
        showToast(Strings.Toasts.UPLOADING_EDITS);
        mSuite.discardEditor(false, null);
        mScreen.close();
    }

    @Override
    public void onFailedToQueue(@Nullable Review review, @Nullable ReviewId id, CallbackMessage
            message) {
        showToast(Strings.Toasts.PROBLEM_UPLOADING_EDITS + ": " + message.getMessage());
        mScreen.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getEditor().onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        mSuite.discardEditor(true, new EditorSuite.DiscardListener() {
            @Override
            public void onDiscarded(boolean discardConfirmed) {
                if(discardConfirmed) mEditor.getCurrentScreen().close();
            }
        });
    }

    public static class Builder {
        private Review mReview;
        private ReviewPack.TemplateOrEdit mTemplateOrEdit;

        public Builder setReview(ReviewPack pack) {
            mReview = pack.getReview();
            mTemplateOrEdit = pack.getTemplateOrEdit();
            return this;
        }

        public PresenterReviewBuild build(ApplicationInstance app) {
            LocationClient client = app.getGeolocation().newLocationClient();
            EditorSuite suite = app.getEditor();
            ReviewPublisher publisher = app.getRepository().getReviewPublisher();
            PresenterReviewBuild presenter = new PresenterReviewBuild(app.getUi().getCurrentScreen());
            if(suite.getEditor() == null) {
                if(mReview == null || mTemplateOrEdit == ReviewPack.TemplateOrEdit.TEMPLATE) {
                    suite.createReviewCreator(Settings.BuildReview.DEFAULT_EDIT_MODE, client, mReview);
                } else {
                    suite.createReviewEditor(client, mReview, publisher, presenter);
                }
            }

            presenter.setSuite(suite);
            return presenter;
        }
    }
}
