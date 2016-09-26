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

import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiNewReview<T extends GvData> extends MenuActionItemBasic<T> {
    private final ReviewId mTemplate;

    public MaiNewReview(@Nullable ReviewId template) {
        mTemplate = template;
    }

    @Override
    public void doAction(MenuItem item) {
        if(getParent() == null) return;

        if(mTemplate != null) getCurrentScreen().showToast(Strings.Toasts.COPYING);
        getApp().newBuildScreenLauncher().launch(mTemplate);
    }
}
