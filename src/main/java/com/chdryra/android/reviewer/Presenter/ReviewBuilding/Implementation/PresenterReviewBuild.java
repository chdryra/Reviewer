/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewBuild<GC extends GvDataList<? extends GvDataParcelable>> implements
        ActivityResultListener{

    private final ReviewEditor<GC> mEditor;

    private PresenterReviewBuild(ReviewEditor<GC> editor) {
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

        public void setTemplateReview(Review template) {
            mTemplate = template;
        }

        public PresenterReviewBuild<?> build(ApplicationInstance app) {
            ReviewEditor<?> editor = app.getReviewEditor();
            if (editor == null) editor = app.newReviewEditor(mTemplate);

            return new PresenterReviewBuild<>(editor);
        }
    }
}
