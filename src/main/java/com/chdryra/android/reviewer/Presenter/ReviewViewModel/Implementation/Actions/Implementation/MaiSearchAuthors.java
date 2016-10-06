/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiSearchAuthors<T extends GvData> extends MenuActionItemBasic<T>{
    private static final int REQUEST_CODE = RequestCodeGenerator.getCode("FollowSearch");
    private UiLauncher mLauncher;
    private FactoryReviewView mFactory;

    public MaiSearchAuthors(UiLauncher launcher, FactoryReviewView factory) {
        mLauncher = launcher;
        mFactory = factory;
    }

    @Override
    public void doAction(MenuItem item) {
        if(!isAttached()) return;

        FactoryReviewViewAdapter adapterFactory = mFactory.getAdapterFactory();
        ReviewViewAdapter.Filterable<GvAuthor> adapter = adapterFactory
                .newFollowSearchAdapter(getApp().getAuthentication().getUserSession().getAuthorId());
        LaunchableUi authorSearch = mFactory.newSearchView(adapter, Strings.EditTexts.Hints.AUTHOR_NAME);
        mLauncher.launch(authorSearch, new UiLauncherArgs(REQUEST_CODE));
    }
}
