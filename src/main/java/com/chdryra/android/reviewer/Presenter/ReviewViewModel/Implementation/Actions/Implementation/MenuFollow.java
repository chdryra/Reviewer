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
public class MenuFollow<T extends GvData> extends MenuActionNone<T> {
    private static final int FOLLOW = R.id.menu_item_follow;
    private static final int MENU = R.menu.menu_follow;

    private final MenuActionItem<T> mFollow;

    public MenuFollow(MenuActionItem<T> follow) {
        super(MENU, null, true);
        mFollow = follow;
        mFollow.setParent(this);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mFollow, FOLLOW, false);
    }
}
