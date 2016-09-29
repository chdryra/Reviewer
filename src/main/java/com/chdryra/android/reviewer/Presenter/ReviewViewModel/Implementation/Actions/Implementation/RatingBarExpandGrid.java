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
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarExpandGrid<T extends GvData> extends RatingBarActionNone<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("RbTreePerspective");
    private final FactoryReviewView mFactory;

    public RatingBarExpandGrid(FactoryReviewView factory) {
        mFactory = factory;
    }

    @Override
    public void onClick(View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        getApp().newUiLauncher().launch(getReviewView(expanded), REQUEST_CODE);
    }

    private LaunchableUi getReviewView(ReviewViewAdapter<?> expanded) {
        LaunchableUi ui = expanded.getReviewView();

        if (ui == null) {
            ApplicationInstance app = getApp();
            ui = mFactory.newDefaultView(expanded, app.newReviewLauncher(),
                    app.getUsersManager().getAuthorsRepository());
        }

        return ui;
    }
}
