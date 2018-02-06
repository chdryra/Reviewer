/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiUpAppLevel<T extends GvData> extends MaiUp<T> {
    private final ApplicationInstance mApp;

    public MaiUpAppLevel(ApplicationInstance app) {
        mApp = app;
    }

    @Override
    protected ApplicationInstance getApp() {
        return mApp;
    }

    @Override
    protected CurrentScreen getCurrentScreen() {
        return mApp.getUi().getCurrentScreen();
    }
}
