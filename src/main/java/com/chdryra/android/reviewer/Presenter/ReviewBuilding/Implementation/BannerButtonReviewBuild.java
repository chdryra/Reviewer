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
    private static final ReviewEditor.GridUiType FULL = ReviewEditor.GridUiType.FULL;
    private static final ReviewEditor.GridUiType QUICK = ReviewEditor.GridUiType.QUICK;

    private ReviewEditor.GridUiType mType;

    public BannerButtonReviewBuild(ReviewEditor.GridUiType type) {
        mType = type;
        setTitle(mType.getLabel());
    }

    @Override
    public void onClick(View v) {
        mType = mType == FULL ? QUICK : FULL;
        setTitle(mType.getLabel());
        notifyListeners();
    }

    public ReviewEditor.GridUiType getUiType() {
        return mType;
    }
}
