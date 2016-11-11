/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuReviewPreview extends MenuActionNone<GvData> {
    private AppInstanceAndroid mApp;

    public MenuReviewPreview(String title, AppInstanceAndroid app) {
        super(title);
        mApp = app;
        setupActionBar();
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mApp.getUi().getCurrentScreen();
    }

    @Override
    protected ApplicationInstance getApp() {
        return mApp;
    }
}
