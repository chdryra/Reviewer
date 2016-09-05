/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements MenuAction<T> {
    private static final int MENU_UP_ID = android.R.id.home;
    private static final ActivityResultCode RESULT_UP = ActivityResultCode.UP;

    private final String mTitle;
    private final SparseArray<MenuActionItemInfo> mActionItems;
    private int mMenuId = -1;
    private boolean mDisplayHomeAsUp = true;

    public MenuActionNone() {
        this(-1, null, true);
    }

    protected MenuActionNone(int menuId, @Nullable String title, boolean displayHomeAsUp) {
        mMenuId = menuId;
        mTitle = title;
        mDisplayHomeAsUp = displayHomeAsUp;
        mActionItems = new SparseArray<>();
        if (mDisplayHomeAsUp) bindMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
    }

    public MenuActionNone(String title) {
        this(-1, title, true);
    }

    protected void addMenuItems() {
    }

    protected void sendResult(ActivityResultCode result) {
        getApp().setReturnResult(result);
    }

    protected void doUpSelected() {
        returnToPreviousActivity();
    }

    @Override
    public boolean hasOptionsMenu() {
        return mMenuId != -1;
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        if (hasOptionsMenu()) inflater.inflate(mMenuId, menu);
    }

    @Override
    public void bindMenuActionItem(MenuActionItem item, int itemId, boolean finishActivity) {
        mActionItems.put(itemId, new MenuActionItemInfo(item, finishActivity));
    }

    @Override
    public boolean onItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        MenuActionItemInfo actionItem = mActionItems.get(itemId);
        if (actionItem != null) {
            actionItem.mItem.doAction(item);
            if (actionItem.mCloseScreen) getApp().getCurrentScreen().close();
            return true;
        }

        return false;
    }

    @Override
    public void onAttachReviewView() {
        CurrentScreen screen = getApp().getCurrentScreen();
        if (screen.hasActionBar()) {
            screen.setHomeAsUp(mDisplayHomeAsUp);
            screen.setTitle(mTitle);
            addMenuItems();
        }
    }

    private MenuActionItem getUpActionItem() {
        return new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                doUpSelected();
                sendResult(RESULT_UP);
            }
        };
    }

    private void returnToPreviousActivity() {
        getApp().getCurrentScreen().returnToPrevious();
    }

    private class MenuActionItemInfo {
        private final boolean mCloseScreen;
        private final MenuActionItem mItem;

        private MenuActionItemInfo(MenuActionItem item, boolean closeScreen) {
            mItem = item;
            mCloseScreen = closeScreen;
        }
    }
}
