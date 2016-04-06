/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final String TAG = "GridItemLauncher:";

    private FactoryReviewViewLaunchable mLaunchableFactory;
    private LaunchableUiLauncher mLauncher;

    public GridItemLauncher(FactoryReviewViewLaunchable launchableFactory,
                            LaunchableUiLauncher launcher) {
        mLauncher = launcher;
        mLaunchableFactory = launchableFactory;
    }

    protected LaunchableUiLauncher getLauncher() {
        return mLauncher;
    }

    protected void launch(LaunchableUi ui, int requestCode, Bundle args) {
        mLauncher.launch(ui, getActivity(), requestCode, args);
    }

    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?> expanded) {
        LaunchableUi ui = getLaunchableUi(expanded);
        launch(ui, RequestCodeGenerator.getCode(TAG + ui.getLaunchTag()), new Bundle());
    }

    private LaunchableUi getLaunchableUi(ReviewViewAdapter<?> expanded) {
        LaunchableUi screen = expanded.getReviewView();
        if (screen == null) screen = mLaunchableFactory.newViewScreen(expanded);
        return screen;
    }
}
