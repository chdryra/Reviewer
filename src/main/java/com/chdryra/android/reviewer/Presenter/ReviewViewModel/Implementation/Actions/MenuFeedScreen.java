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
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewAsync;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuFeedScreen extends MenuActionNone<GvReviewAsync> {
    private static final int LAUNCH_BUILD_SCREEN = RequestCodeGenerator.getCode("BuildScreenNewReview");

    public static final int LOGOUT = R.id.menu_item_settings_logout;
    public static final int NEW_REVIEW = R.id.menu_item_new_review;
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
        bindMenuActionItem(launchBuildScreen(), NEW_REVIEW, false);
        bindMenuActionItem(logout(), LOGOUT, false);
    }

    @NonNull
    private MenuActionItem logout() {
        return new MenuActionItem() {
            @Override
            public void doAction(Context context, MenuItem item) {
                ApplicationInstance.getInstance(context).logout(getActivity());
            }
        };
    }

    @NonNull
    private MenuActionItem launchBuildScreen() {
        return new MenuActionItem() {
            @Override
            public void doAction(Context context, MenuItem item) {
                mUiLauncher.launch(mBuildScreenUi, getActivity(), LAUNCH_BUILD_SCREEN,
                        new Bundle());
            }
        };
    }
}
