/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActionBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewDataEditorActionBasic<T extends GvData> extends ReviewViewActionBasic<T> {
    private ReviewDataEditor<T> mEditor;

    ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    @Override
    public void onAttachReviewView() {
        try {
            mEditor = (ReviewDataEditor<T>)getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }
    }
}
