/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BannerButtonLaunchFormatted extends BannerButtonActionNone<GvSize.Reference> {
    private final ReviewId mReview;
    private final ReviewLauncher mLauncher;

    public BannerButtonLaunchFormatted(ReviewId review, ReviewLauncher launcher) {
        super("Press for Formatted");
        mReview = review;
        mLauncher = launcher;
    }

    @Override
    public void onClick(View v) {
        mLauncher.launchReviewFormatted(mReview);
        notifyListeners();
    }
}
