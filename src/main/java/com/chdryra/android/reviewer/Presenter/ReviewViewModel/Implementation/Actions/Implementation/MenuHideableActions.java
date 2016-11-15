/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.MenuItem;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MenuHideableActions<T extends GvData> extends MenuActionNone<T> {
    private final ArrayList<Integer> mItemIds;

    public MenuHideableActions(String title, int menuId) {
        super(menuId, title, true);
        mItemIds = new ArrayList<>();
    }

    void bindHideableItem(MenuActionItem<T> item, int itemId, boolean finishActivity) {
        mItemIds.add(itemId);
        bindMenuActionItem(item, itemId, finishActivity);
    }

    @Override
    protected void onMenuInflated() {
        for(int itemId : mItemIds) {
            if (hideCondition(itemId)) {
                MenuItem item = getItem(itemId);
                if (item != null) item.setVisible(false);
            }
        }
    }

    protected abstract boolean hideCondition(int itemId);
}
