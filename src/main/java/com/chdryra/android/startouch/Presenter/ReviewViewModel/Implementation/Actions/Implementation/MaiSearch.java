/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import android.view.MenuItem;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSearch<T extends GvData> extends MenuActionItemBasic<T> {
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("Search");

    private UiLauncher mLauncher;
    private FactoryReviewView mFactory;

    public MaiSearch(UiLauncher launcher, FactoryReviewView factory) {
        mLauncher = launcher;
        mFactory = factory;
    }

    @Override
    public void doAction(MenuItem item) {
        mLauncher.launch(mFactory.newSearchView(), new UiLauncherArgs(REQUEST_CODE));
    }
}
