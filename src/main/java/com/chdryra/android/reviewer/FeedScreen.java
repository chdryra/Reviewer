/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen {
    public static ReviewView newScreen(Context context) {
        ReviewView view = new ReviewView(Administrator.get(context).getPublishedReviews());

        view.setAction(new FeedScreenMenu());

        ReviewView.ReviewViewParams params = view.getParams();
        params.cellHeight = ReviewView.CellDimension.FULL;
        params.cellWidth = ReviewView.CellDimension.FULL;
        params.subjectIsVisible = false;
        params.ratingIsVisible = false;
        params.bannerButtonIsVisible = false;
        params.gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;
        params.coverManager = false;

        return view;
    }

    public static class FeedScreenMenu extends ReviewViewAction.MenuAction {
        public static final  int MENU_NEW_REVIEW_ID = R.id.menu_item_new_review;
        private static final int MENU               = R.menu.fragment_feed;

        private FeedScreenMenu() {
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

            Administrator admin = Administrator.get(getActivity());
            admin.newReviewBuilder();
            Intent i = new Intent(activity, ActivityReviewView.class);
            admin.packView(BuildScreen.newScreen(getActivity()), i);

            getActivity().startActivity(i);
        }
    }
}
