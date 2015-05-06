/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.database.DataSetObserver;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Controller.ReviewBuilder;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenChildren {
    public static class Menu extends EditScreen.Menu {
        public static final  int MENU_DELETE_ID  = R.id.menu_item_delete;
        public static final  int MENU_DONE_ID    = R.id.menu_item_done;
        public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
        private static final int MENU            = R.menu.fragment_review_children;

        private final MenuItemChildrenRatingAverage mActionItem;
        private final DataSetObserver               mObserver;

        public Menu() {
            super(GvChildList.TYPE.getDataName(), GvChildList.TYPE.getDataName(), false, true,
                    MENU);
            mActionItem = new MenuItemChildrenRatingAverage();
            mObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    if (getEditor().isRatingAverage()) {
                        mActionItem.setAverageRating();
                    }
                }
            };
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            ReviewView.Editor editor = getEditor();

            editor.registerGridDataObserver(mObserver);
            ReviewBuilder.DataBuilder adapter = (ReviewBuilder.DataBuilder) editor.getAdapter();
            editor.setRatingAverage(adapter.getParentBuilder().isRatingAverage());
        }

        @Override
        protected void addMenuItems() {
            addDefaultDeleteActionItem(MENU_DELETE_ID);
            addDefaultDoneActionItem(MENU_DONE_ID);
            addMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
        }

        @Override
        public void onUnattachReviewView() {
            getReviewView().unregisterGridDataObserver(mObserver);
            super.onUnattachReviewView();
        }

        public class MenuItemChildrenRatingAverage implements MenuAction.MenuActionItem {
            public void setAverageRating() {
                float rating = 0;
                GvChildList children = (GvChildList) getGridData();
                for (GvChildList.GvChildReview child : children) {
                    rating += child.getRating() / children.size();
                }

                getEditor().setRating(rating);
            }

            @Override
            public void doAction(MenuItem item) {
                getEditor().setRatingAverage(true);
                setAverageRating();
            }
        }
    }
}
