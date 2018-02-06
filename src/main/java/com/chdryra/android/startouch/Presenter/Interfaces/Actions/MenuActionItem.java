/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Actions;

import android.view.MenuItem;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface MenuActionItem<T extends GvData> {
    void setParent(MenuAction<T> parent);

    void doAction(MenuItem item);

    void onAttachReviewView();

    void onDetachReviewView();

    void onInflateMenu();
}
