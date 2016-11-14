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

import com.chdryra.android.reviewer.Application.Implementation.Settings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewEditorSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewEdit<GC extends GvDataList<? extends GvDataParcelable>> implements
        ActivityResultListener{

    private final ReviewEditor<GC> mEditor;

    private PresenterReviewEdit(ReviewEditor<GC> editor) {
        mEditor = editor;
    }

    public ReviewEditor getEditor() {
        return mEditor;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mEditor.onActivityResult(requestCode, resultCode, data);
    }

    public static class Builder {
        private Review mTemplate;

        public Builder setTemplate(@Nullable Review template) {
            mTemplate = template;
            return this;
        }

        public PresenterReviewEdit<?> build(ApplicationInstance app) {
            ReviewEditorSuite suite = app.getReviewEditor();
            ReviewEditor<?> editor = suite.getEditor();
            if (editor == null) {
                editor = suite.createEditor(Settings.BuildReview.DEFAULT_EDIT_MODE,
                        app.getLocationServices().newLocationClient(), mTemplate);
            }

            return new PresenterReviewEdit<>(editor);
        }
    }
}
