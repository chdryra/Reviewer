/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 29 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by: Rizwan Choudrey
 * On: 29/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreenMenu extends ReviewViewAction.MenuAction {
    public static final  int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
    private static final int MENU               = R.menu.fragment_feed;

    public FeedScreenMenu() {
        super(MENU, null, false);
    }

    @Override
    protected void addMenuItems() {
        addMenuActionItem(new MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                requestNewReviewIntent();
            }
        }, MENU_NEW_REVIEW_ID, false);
    }

    private void requestNewReviewIntent() {
        Activity activity = getActivity();
        if (activity == null) return;

        Administrator.get(getActivity()).newReviewBuilder();
        Intent i = new Intent(activity, ActivityReviewView.class);
        ActivityReviewView.packParameters(GvDataList.GvType.BUILD_REVIEW, true, i);
        getActivity().startActivity(i);
    }
}
