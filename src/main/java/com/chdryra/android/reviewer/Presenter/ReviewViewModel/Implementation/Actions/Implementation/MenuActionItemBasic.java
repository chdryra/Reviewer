/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MenuActionItemBasic<T extends GvData> implements MenuAction.MenuActionItem<T> {
    private MenuAction<T> mParent;

    @Override
    public void setParent(MenuAction<T> parent) {
        mParent = parent;
    }

    @Override
    public void doAction(MenuItem item) {

    }

    @Override
    public void onAttachReviewView() {

    }

    @Override
    public void onDetachReviewView() {

    }

    @Override
    public void onInflateMenu() {

    }

    @Nullable
    MenuItem getMenuItem() {
        return mParent.getItem(this);
    }

    protected boolean isAttached() {
        return mParent != null;
    }

    protected ReviewView<T> getReviewView() {
        return mParent.getReviewView();
    }

    protected ApplicationInstance getApp() {
        return mParent.getReviewView().getApp();
    }

    protected CurrentScreen getCurrentScreen() {
        return mParent.getCurrentScreen();
    }
}
