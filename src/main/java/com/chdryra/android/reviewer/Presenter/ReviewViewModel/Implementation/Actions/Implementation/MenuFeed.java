/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuFeed<T extends GvData> extends MenuActionNone<T> {
    private static final int MENU = R.menu.menu_feed;
    private static final int LOGOUT = R.id.menu_item_logout;
    private static final int SEARCH = R.id.menu_item_search;
    private static final int NEW_REVIEW = R.id.menu_item_new_review;

    public MenuFeed(MenuActionItem<T> newReview,
                    MenuActionItem<T> search,
                    MenuActionItem<T> logout) {
        super(MENU, "", false);
        bindMenuActionItem(newReview, NEW_REVIEW, false);
        bindMenuActionItem(search, SEARCH, false);
        bindMenuActionItem(logout, LOGOUT, false);
    }
}
