/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuNewReview<T extends GvData> extends MenuActionNone<T> {
    private static final int LOGOUT = R.id.menu_item_settings_logout;
    private static final int NEW_REVIEW = R.id.menu_item_new_review;
    private static final int MENU = R.menu.menu_feed;

    private final ReviewId mTemplate;
    private final BuildScreenLauncher mLauncher;

    public MenuNewReview(BuildScreenLauncher launcher) {
        this(launcher, null);
    }

    public MenuNewReview(BuildScreenLauncher launcher, @Nullable ReviewId template) {
        super(MENU, null, false);
        mTemplate = template;
        mLauncher = launcher;
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
            public void doAction(MenuItem item) {
                getApp().logout();
            }
        };
    }

    @NonNull
    private MenuActionItem launchBuildScreen() {
        return new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                mLauncher.launch(getApp(), mTemplate);
            }
        };
    }
}
