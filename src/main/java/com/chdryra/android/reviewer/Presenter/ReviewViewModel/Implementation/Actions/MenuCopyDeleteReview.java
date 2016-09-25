/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuCopyDeleteReview<T extends GvData> extends MenuActionNone<T> implements AlertListener{
    private static final int COPY_REVIEW = R.id.menu_item_copy_review2;
    private static final int DELETE_REVIEW = R.id.menu_item_delete_review;
    private static final int MENU = R.menu.menu_copy_delete_review;

    private final MenuActionItem<T> mCopyReview;
    private final MaiDeleteReview<T> mDeleteReview;

    public MenuCopyDeleteReview(MenuActionItem<T> copyReview, @Nullable MaiDeleteReview<T> deleteReview, String title) {
        super(MENU, title, false);
        mCopyReview = copyReview;
        mDeleteReview = deleteReview;
        mCopyReview.setParent(this);
        if(mDeleteReview != null) mDeleteReview.setParent(this);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mCopyReview, COPY_REVIEW, false);
        if(mDeleteReview != null) bindMenuActionItem(mDeleteReview, DELETE_REVIEW, false);
    }

    @Override
    protected void onInflateMenu() {
        super.onInflateMenu();
        if(mDeleteReview == null) {
            MenuItem item = getItem(DELETE_REVIEW);
            if(item != null) item.setVisible(false);
        }
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        if(mDeleteReview != null) mDeleteReview.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if(mDeleteReview != null) mDeleteReview.onAlertPositive(requestCode, args);
    }
}
