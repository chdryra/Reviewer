/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiNewReview<T extends GvData> extends MenuActionItemBasic<T> {
    private final BuildScreenLauncher mLauncher;
    private final ReviewId mTemplate;

    public MaiNewReview(BuildScreenLauncher launcher, @Nullable ReviewId template) {
        mLauncher = launcher;
        mTemplate = template;
    }

    @Override
    public void doAction(MenuItem item) {

        if(getParent() != null) {
            ApplicationInstance app = getParent().getApp();
            if(mTemplate != null) app.getCurrentScreen().showToast(Strings.Toasts.COPYING);
            mLauncher.launch(app, mTemplate);
        }
    }
}
