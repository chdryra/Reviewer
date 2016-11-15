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
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiNewReview<T extends GvData> extends MenuActionItemBasic<T> {
    private UiLauncher mLauncher;

    public MaiNewReview(UiLauncher launcher) {
        mLauncher = launcher;
    }

    @Override
    public void doAction(MenuItem item) {
        mLauncher.launchEditUi(null);
    }
}
