/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuReviewOptions;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuReviewOptionsAppLevel extends MenuReviewOptions<GvData> {
    private UiSuite mUi;

    public MenuReviewOptionsAppLevel(String title,
                                     MenuActionItem<GvData> upAction,
                                     MenuActionItem<GvData> reviewOptions,
                                     UiSuite ui) {
        super(title, upAction, reviewOptions);
        mUi = ui;
        setupActionBar();
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mUi.getCurrentScreen();
    }
}
