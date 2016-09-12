/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiFollow<T extends GvData> extends MenuActionItemBasic<T>{
    @Override
    public void doAction(MenuItem item) {
        if(getParent() != null) {
            ApplicationInstance app = getParent().getApp();
            LaunchableConfig authorSearch = app.getConfigUi().getAuthorSearch();
            app.getUiLauncher().launch(authorSearch, authorSearch.getRequestCode());
        }
    }
}
