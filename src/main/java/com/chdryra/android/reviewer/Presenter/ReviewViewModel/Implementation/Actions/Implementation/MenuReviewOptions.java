/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuReviewOptions<T extends GvData> extends MenuActionNone<T> {
    private static final int OPTIONS = R.id.menu_item_options;
    private static final int MENU = R.menu.menu_review_options;

    private final MenuActionItem<T> mReviewOptions;

    public MenuReviewOptions(MenuActionItem<T> reviewOptions, String title) {
        super(MENU, title, true);
        mReviewOptions = reviewOptions;
        mReviewOptions.setParent(this);
    }

    @Override
    protected void addMenuItems() {
        bindMenuActionItem(mReviewOptions, OPTIONS, false);
    }
}
