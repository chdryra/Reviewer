/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActionBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewEditorActionBasic<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewViewActionBasic<GC> {
    private ReviewEditor<GC> mEditor;

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        try {
            mEditor = (ReviewEditor<GC>) getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException("Attached ReviewView should be Editor!", e);
        }
    }

    ReviewEditor<GC> getEditor() {
        return mEditor;
    }
}
