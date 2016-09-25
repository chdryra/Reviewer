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

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final String TAG = "GridItemLauncher:";

    private final FactoryReviewView mReviewViewFactory;

    public GridItemLauncher(FactoryReviewView reviewViewFactory) {
        mReviewViewFactory = reviewViewFactory;
    }

    void launch(LaunchableUi ui, int requestCode, Bundle args) {
        getApp().getUiLauncher().launch(ui, requestCode, args);
    }

    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?> expanded) {
        ReviewView<?> ui = getReviewView(expanded);
        launch(ui, RequestCodeGenerator.getCode(TAG + ui.getLaunchTag()), new Bundle());
    }

    private <T2 extends GvData> ReviewView<T2> getReviewView(ReviewViewAdapter<T2> expanded) {
        ReviewView<T2> ui = expanded.getReviewView();

        if (ui == null) {
            ApplicationInstance app = getApp();
            ui = mReviewViewFactory.newDefaultView(expanded, app.newReviewLauncher(),
                    app.getUsersManager().getAuthorsRepository(), app.getUserSession());;
        }

        return ui;
    }
}
