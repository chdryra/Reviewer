/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements MenuAction<T> {
    private static final int MENU_UP_ID = android.R.id.home;

    private final String mTitle;
    private final Map<Integer, MenuActionItemInfo> mActionItems;
    private int mMenuId = -1;
    private boolean mDisplayHomeAsUp = true;
    private Menu mMenu;

    public MenuActionNone(String title) {
        this(-1, title, true);
    }

    public MenuActionNone(String title, MenuActionItem<T> upAction) {
        this(-1, title, upAction);
    }

    protected MenuActionNone(int menuId, String title, boolean displayHomeAsUp) {
        this(menuId, title, displayHomeAsUp ? new MaiUp<T>() : null);
    }

    protected MenuActionNone(int menuId, String title, @Nullable MenuActionItem<T> upAction) {
        mMenuId = menuId;
        mTitle = title;
        mDisplayHomeAsUp = upAction != null;
        mActionItems = new HashMap<>();
        if (upAction != null) bindMenuActionItem(upAction, MENU_UP_ID, false);
    }

    @Nullable
    protected MenuItem getItem(int itemId) {
        return mMenu != null ? mMenu.findItem(itemId) : null;
    }

    @Override
    public boolean hasMenu() {
        return mMenuId != -1;
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        if (hasMenu()) inflater.inflate(mMenuId, menu);
        for (Map.Entry<Integer, MenuActionItemInfo> entry : mActionItems.entrySet()) {
            entry.getValue().mItem.onInflateMenu();
        }
        onMenuInflated();
    }

    protected void onMenuInflated() {

    }

    @Override
    public Menu getMenu() {
        return mMenu;
    }

    @Override
    public void bindMenuActionItem(MenuActionItem<T> item, int itemId, boolean finishActivity) {
        item.setParent(this);
        mActionItems.put(itemId, new MenuActionItemInfo(item, finishActivity));
    }

    @Override
    public boolean onItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        MenuActionItemInfo actionItem = mActionItems.get(itemId);
        if (actionItem != null) {
            actionItem.mItem.doAction(item);
            if (actionItem.mCloseScreen) getCurrentScreen().close();
            return true;
        }

        return false;
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        setupActionBar();
        for (Map.Entry<Integer, MenuActionItemInfo> entry : mActionItems.entrySet()) {
            entry.getValue().mItem.onAttachReviewView();
        }
    }

    protected void setupActionBar() {
        CurrentScreen screen = getCurrentScreen();
        if (screen.hasActionBar()) {
            screen.setHomeAsUp(mDisplayHomeAsUp);
            screen.setTitle(mTitle);
        }
    }

    @Override
    public void onDetachReviewView() {
        super.onDetachReviewView();
        for (Map.Entry<Integer, MenuActionItemInfo> entry : mActionItems.entrySet()) {
            entry.getValue().mItem.onDetachReviewView();
        }
    }

    @Override
    @Nullable
    public MenuItem getItem(MenuActionItem<T> item) {
        MenuItem mi = null;
        for (Map.Entry<Integer, MenuActionItemInfo> entry : mActionItems.entrySet()) {
            MenuActionItemInfo info = entry.getValue();
            if (info.mItem.equals(item)) {
                mi = mMenu != null ? mMenu.findItem(entry.getKey()) : null;
                break;
            }
        }

        return mi;
    }

    private static class MenuActionItemInfo {
        private final boolean mCloseScreen;
        private final MenuActionItem<?> mItem;

        private MenuActionItemInfo(MenuActionItem<?> item, boolean closeScreen) {
            mItem = item;
            mCloseScreen = closeScreen;
        }
    }
}
