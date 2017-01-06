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

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Application.Implementation.Settings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewBuild implements ActivityResultListener{

    private final ReviewEditorSuite mSuite;
    private ReviewEditor<?> mEditor;

    private PresenterReviewBuild(ReviewEditorSuite suite, LocationClient locationClient, @Nullable Review template) {
        mSuite = suite;
        mEditor = suite.getEditor();
        if (mEditor == null) {
            mEditor = mSuite.createEditor(Settings.BuildReview.DEFAULT_EDIT_MODE, locationClient, template);
        }
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
        private Review mTemplate;

        public Builder setTemplate(@Nullable Review template) {
            mTemplate = template;
            return this;
        }

        public PresenterReviewBuild build(ApplicationInstance app) {
            return new PresenterReviewBuild(app.getReviewEditor(),
                    app.getLocationServices().newLocationClient(), mTemplate);
        }
    }
}
