/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BannerButtonReviewBuild<GC extends GvDataList<? extends GvDataParcelable>>
        extends BannerButtonActionNone<GC> {
    private static final ReviewEditor.EditMode FULL = ReviewEditor.EditMode.FULL;
    private static final ReviewEditor.EditMode QUICK = ReviewEditor.EditMode.QUICK;

    private ReviewEditor.EditMode mMode;

    public BannerButtonReviewBuild(ReviewEditor.EditMode defaultMode) {
        mMode = defaultMode;
        setTitle(mMode.getLabel());
    }

    @Override
    public void onClick(View v) {
        mMode = mMode == FULL ? QUICK : FULL;
        setTitle(mMode.getLabel());
        notifyListeners();
    }

    public ReviewEditor.EditMode getEditMode() {
        return mMode;
    }
}
