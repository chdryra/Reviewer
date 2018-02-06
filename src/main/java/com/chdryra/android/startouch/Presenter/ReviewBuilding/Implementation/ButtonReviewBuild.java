/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonReviewBuild<GC extends GvDataList<? extends GvDataParcelable>>
        extends ButtonActionNone<GC> {
    private static final ReviewEditor.EditMode FULL = ReviewEditor.EditMode.FULL;
    private static final ReviewEditor.EditMode QUICK = ReviewEditor.EditMode.QUICK;

    private ReviewEditor.EditMode mMode;

    public ButtonReviewBuild(ReviewEditor.EditMode defaultMode) {
        mMode = defaultMode;
        setTitle(mMode.getLabel());
    }

    @Override
    public void onClick(View v) {
        mMode = mMode == FULL ? QUICK : FULL;
        setTitle(mMode.getLabel());
        notifyListeners();
    }

    ReviewEditor.EditMode getEditMode() {
        return mMode;
    }
}
