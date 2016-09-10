/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuNewReview<T extends GvData> extends MenuActionNone<T> {
    private static final int NEW_REVIEW = R.id.menu_item_new_review;
    private static final int MENU = R.menu.menu_new_review;

    private final MenuActionItem<T> mNewReview;

    public MenuNewReview(MenuActionItem<T> newReview) {
        super(MENU, null, false);
        mNewReview = newReview;
        mNewReview.setParent(this);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mNewReview, NEW_REVIEW, false);
    }
}
