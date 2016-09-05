/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

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

    private final MaiSplitComments<GvComment.Reference> mSplitter;

    public MenuComments() {
        super(MENU, GvComment.TYPE.getDataName(), true);
        mSplitter = new MaiSplitComments<>(this);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mSplitter, MENU_SPLIT_ID, false);
    }
}
