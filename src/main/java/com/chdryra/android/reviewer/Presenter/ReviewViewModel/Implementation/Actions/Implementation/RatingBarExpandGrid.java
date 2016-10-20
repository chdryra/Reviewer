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
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarExpandGrid<T extends GvData> extends RatingBarActionNone<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode(RatingBarExpandGrid.class);

    private final UiLauncher mLauncher;
    private final FactoryReviewView mFactory;

    public RatingBarExpandGrid(UiLauncher launcher, FactoryReviewView factory) {
        mLauncher = launcher;
        mFactory = factory;
    }

    @Override
    public void onClick(View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridData();
        if (expanded == null) return;
        mLauncher.launch(mFactory.newDefaultView(expanded), new UiLauncherArgs(REQUEST_CODE));
    }
}
