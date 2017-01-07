/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Application.Implementation.Settings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewPack;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewBuild implements ActivityResultListener{

    private final ReviewEditorSuite mSuite;
    private ReviewEditor<?> mEditor;

    private PresenterReviewBuild(ReviewEditorSuite suite) {
        mSuite = suite;
        mEditor = suite.getEditor();
    }

    public ReviewEditor getEditor() {
        return mEditor;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getEditor().onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        mSuite.discardEditor(true, new ReviewEditorSuite.DiscardListener() {
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
            LocationClient client = app.getLocationServices().newLocationClient();
            ReviewEditorSuite suite = app.getReviewEditor();

            if(suite.getEditor() == null) {
                if(mReview == null || mTemplateOrEdit == ReviewPack.TemplateOrEdit.TEMPLATE) {
                    suite.newReviewCreator(Settings.BuildReview.DEFAULT_EDIT_MODE, client, mReview);
                } else {
                    suite.newReviewEditor(client, mReview);
                }
            }

            return new PresenterReviewBuild(suite);
        }
    }
}
