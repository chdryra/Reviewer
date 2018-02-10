/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.view.View;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLauncher<T extends GvData> extends GridItemExpander<T> {
    private static final String TAG = TagKeyGenerator.getTag(GridItemLauncher.class);

    private final UiLauncher mLauncher;
    private final FactoryReviewView mFactory;

    public GridItemLauncher(UiLauncher launcher, FactoryReviewView factory) {
        mLauncher = launcher;
        mFactory = factory;
    }

    @Override
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?> expanded) {
        ReviewView<?> ui = mFactory.newReviewView(expanded);
        int code = RequestCodeGenerator.getCode(TAG + ui.getLaunchTag());
        mLauncher.launch(ui, new UiLauncherArgs(code));
    }
}
