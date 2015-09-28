/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.view.MenuItem;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenChildren {
    private static final GvDataType<GvChildReviewList.GvChildReview> TYPE =
            GvChildReviewList.GvChildReview.TYPE;

    public static class Menu extends EditScreen.Menu implements GridDataObservable.GridDataObserver {
        public static final  int MENU_DELETE_ID  = R.id.menu_item_delete;
        public static final  int MENU_DONE_ID    = R.id.menu_item_done;
        public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
        private static final int MENU = R.menu.menu_edit_children;

        private final MenuItemChildrenRatingAverage mActionItem;

        public Menu() {
            super(TYPE.getDataName(), TYPE.getDataName(), false, true, MENU);
            mActionItem = new MenuItemChildrenRatingAverage();
        }

        @Override
        public void onGridDataChanged() {
            if (getEditor().isRatingAverage()) mActionItem.setAverageRating();
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();

            ReviewView.Editor editor = getEditor();
            editor.registerGridDataObserver(this);
            ReviewBuilderAdapter.DataBuilderAdapter adapter = (ReviewBuilderAdapter
                    .DataBuilderAdapter) editor.getAdapter();
            editor.setRatingAverage(adapter.getParentBuilder().isRatingAverage());
        }

        @Override
        protected void addMenuItems() {
            bindDefaultDeleteActionItem(MENU_DELETE_ID);
            bindDefaultDoneActionItem(MENU_DONE_ID);
            bindMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
        }

        @Override
        public void onUnattachReviewView() {
            getReviewView().unregisterGridDataObserver(this);
            super.onUnattachReviewView();
        }

        public class MenuItemChildrenRatingAverage implements MenuAction.MenuActionItem {
            public void setAverageRating() {
                float rating = 0;
                GvChildReviewList children = (GvChildReviewList) getGridData();
                for (GvChildReviewList.GvChildReview child : children) {
                    rating += child.getRating() / children.size();
                }

                getEditor().setRating(rating);
            }

            @Override
            public void doAction(Context context, MenuItem item) {
                getEditor().setRatingAverage(true);
                setAverageRating();
            }
        }
    }
}
