/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MaiSplitCommentsBasic<T extends GvData> extends MenuActionItemBasic<T> {
    private static final int UNSPLIT_ICON = R.drawable.ic_action_return_from_full_screen;
    private static final int SPLIT_ICON = R.drawable.ic_action_full_screen;

    private boolean mIsSplit = false;

    protected abstract void doSplit(boolean doSplit);

    @Override
    public void onInflateMenu() {
        MenuItem menuItem = getMenuItem();
        if(menuItem != null) updateMenuItem(menuItem);
    }

    @Override
    public void doAction(MenuItem item) {
        mIsSplit = !mIsSplit;
        updateMenuUi(item);
        doSplit(mIsSplit);
    }

    private void updateMenuUi(MenuItem item) {
        updateMenuItem(item);
        getCurrentScreen().showToast(mIsSplit ?
                Strings.Toasts.SPLIT_COMMENT : Strings.Toasts.UNSPLIT_COMMENT);
    }

    private void updateMenuItem(MenuItem item) {
        item.setIcon(mIsSplit ? UNSPLIT_ICON : SPLIT_ICON);
        item.setTitle(mIsSplit ? Strings.Menu.SHOW_HEADLINES : Strings.Menu.SHOW_SENTENCES);
    }
}
