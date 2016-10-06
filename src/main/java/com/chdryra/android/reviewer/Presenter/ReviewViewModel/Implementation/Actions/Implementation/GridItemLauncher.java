/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final String TAG = "GridItemLauncher:";

    private final UiLauncher mUiLauncher;
    private final FactoryReviewView mViewFactory;

    public GridItemLauncher(UiLauncher uiLauncher, FactoryReviewView viewFactory) {
        mUiLauncher = uiLauncher;
        mViewFactory = viewFactory;
    }

    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?> expanded) {
        ReviewView<?> ui = getReviewView(expanded);
        int code = RequestCodeGenerator.getCode(TAG + ui.getLaunchTag());
        mUiLauncher.launch(ui, new UiLauncherArgs(code));
    }

    private <T2 extends GvData> ReviewView<T2> getReviewView(ReviewViewAdapter<T2> expanded) {
        ReviewView<T2> ui = expanded.getReviewView();
        if (ui == null) ui = mViewFactory.newDefaultView(expanded);

        return ui;
    }
}
