/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarExpandGrid<T extends GvData> extends RatingBarActionNone<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("RbTreePerspective");
    FactoryReviewViewLaunchable mLaunchableFactory;
    FactoryUiLauncher mLauncher;

    public RatingBarExpandGrid(FactoryReviewViewLaunchable launchableFactory,
                               FactoryUiLauncher launcher) {
        mLaunchableFactory = launchableFactory;
        mLauncher = launcher;
    }

    @Override
    public void onClick(View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        mLauncher.launch(getLaunchableUi(expanded), getActivity(), REQUEST_CODE);
    }

    private LaunchableUi getLaunchableUi(ReviewViewAdapter<?> expanded) {
        LaunchableUi ui = expanded.getReviewView();
        if (ui == null) ui = mLaunchableFactory.newViewScreen(expanded);
        return ui;
    }
}
