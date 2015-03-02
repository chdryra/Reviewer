/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.view.MenuItem;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenMenu extends ReviewViewAction.MenuAction {
    public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU            = R.menu.fragment_review_options;
    private MenuActionItem    mActionItem;
    private ReviewView.Editor mEditor;

    public BuildScreenMenu(String title) {
        super(MENU, title, true);
        mActionItem = new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                ReviewBuilder builder = (ReviewBuilder) getAdapter();
                builder.setRatingIsAverage(true);
                mEditor.setRating(builder.getRating());
            }
        };
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = ReviewView.Editor.cast(getReviewView());
    }

    @Override
    protected void addMenuItems() {
        addMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
    }
}
