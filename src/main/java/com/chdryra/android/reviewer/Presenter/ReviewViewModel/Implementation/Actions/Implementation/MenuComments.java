/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuComments extends MenuActionNone<GvComment.Reference> {
    private static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
    private static final int MENU = R.menu.menu_view_comments;

    private final MenuActionItem<GvComment.Reference> mSplitter;

    public MenuComments(MenuActionItem<GvComment.Reference> splitter) {
        super(MENU, GvComment.TYPE.getDataName(), true);
        mSplitter = splitter;
        mSplitter.setParent(this);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
    }
}
