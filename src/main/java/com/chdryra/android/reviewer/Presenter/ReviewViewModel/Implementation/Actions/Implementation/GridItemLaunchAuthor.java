/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLaunchAuthor extends GridItemLauncher<GvAuthor> {
    private ReviewLauncher mLauncher;

    public GridItemLaunchAuthor(UiLauncher launcher, FactoryReviewView launchableFactory) {
        super(launcher, launchableFactory);
        mLauncher = launcher.getReviewLauncher();
    }

    @Override
    public void onGridItemClick(GvAuthor item, int position, View v) {
        mLauncher.launchReviewsList(item.getAuthorId());
    }

    @Override
    public void onGridItemLongClick(GvAuthor item, int position, View v) {
        onGridItemClick(item, position, v);
    }
}
