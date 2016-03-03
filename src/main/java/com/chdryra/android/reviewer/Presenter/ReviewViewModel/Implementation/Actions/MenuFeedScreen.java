/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuFeedScreen extends MenuActionNone<GvReviewOverview> {
    private static final int LAUNCH_BUILD_SCREEN = RequestCodeGenerator.getCode("BuildScreen");

    public static final int MENU_LOGOUT = R.id.menu_item_settings_logout;
    public static final int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
    private static final int MENU = R.menu.menu_feed;

    private LaunchableUiLauncher mUiLauncher;
    private LaunchableUi mBuildScreenUi;

    public MenuFeedScreen(LaunchableUiLauncher uiLauncher,
                          LaunchableUi buildScreenUi) {
        super(MENU, null, false);
        mUiLauncher = uiLauncher;
        mBuildScreenUi = buildScreenUi;
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(new MenuActionItem() {
            @Override
            public void doAction(Context context, MenuItem item) {
                mUiLauncher.launch(mBuildScreenUi, getActivity(), LAUNCH_BUILD_SCREEN,
                        new Bundle());
            }
        }, MENU_NEW_REVIEW_ID, false);

        bindMenuActionItem(new MenuActionItem() {
            @Override
            public void doAction(Context context, MenuItem item) {
                ApplicationInstance.getInstance(context).getSocialPlatformList().logout();
            }
        }, MENU_LOGOUT, false);
    }
}
