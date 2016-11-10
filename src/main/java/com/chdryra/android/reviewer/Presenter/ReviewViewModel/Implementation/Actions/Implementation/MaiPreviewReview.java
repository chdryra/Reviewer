/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiPreviewReview<T extends GvData> extends MenuActionItemBasic<T> {
    private ReviewLauncher mLauncher;

    public MaiPreviewReview(ReviewLauncher launcher) {
        mLauncher = launcher;

    }

    @Override
    public void doAction(MenuItem item) {
        if(!isAttached()) return;
        mLauncher.launchFormatted(null);
    }
}
