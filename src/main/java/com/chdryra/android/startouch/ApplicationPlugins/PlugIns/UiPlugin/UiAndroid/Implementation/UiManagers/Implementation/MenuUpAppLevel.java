/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MenuActionNone;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuUpAppLevel extends MenuActionNone<GvData> {
    private UiSuite mUi;

    public MenuUpAppLevel(String title, MenuActionItem<GvData> upAction, UiSuite ui) {
        super(title, upAction);
        mUi = ui;
        setupActionBar();
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mUi.getCurrentScreen();
    }
}
