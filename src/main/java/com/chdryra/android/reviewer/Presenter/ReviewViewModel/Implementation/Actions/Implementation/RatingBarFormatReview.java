/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.View;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarFormatReview<T extends GvData> extends RatingBarActionNone<T> {
    private final ReviewId mReviewId;
    private final ReviewLauncher mLauncher;

    public RatingBarFormatReview(ReviewId reviewId, ReviewLauncher launcher) {
        mReviewId = reviewId;
        mLauncher = launcher;
    }

    @Override
    public void onClick(View v) {
        getCurrentScreen().showToast(Strings.LOADING);
        mLauncher.launchReviewFormatted(mReviewId);
    }
}
