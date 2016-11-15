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
public class MenuViewComments extends MenuViewData<GvComment.Reference> {
    private static final int MENU = R.menu.menu_view_comments;
    private static final int MENU_SPLIT_ID = R.id.menu_item_split_comment;
    private static final int MENU_OPTIONS_ID = R.id.menu_item_options_comment;

    public MenuViewComments(MenuActionItem<GvComment.Reference> options,
                            MenuActionItem<GvComment.Reference> splitter) {
        super(GvComment.Reference.TYPE, MENU, MENU_OPTIONS_ID, options);
        bindMenuActionItem(splitter, MENU_SPLIT_ID, false);
    }
}
