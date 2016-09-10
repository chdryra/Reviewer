/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.Actions;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface MenuAction<T extends GvData> extends ReviewViewAction<T> {
    interface MenuActionItem<T extends GvData> {
        void setParent(MenuAction<T> parent);

        void doAction(MenuItem item);
    }

    boolean hasOptionsMenu();

    void inflateMenu(Menu menu, MenuInflater inflater);

    void bindMenuActionItem(MenuActionItem item, int itemId, boolean finishActivity);

    boolean onItemSelected(MenuItem item);
}
