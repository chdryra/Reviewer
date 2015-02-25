/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.database.DataSetObserver;
import android.view.MenuItem;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditChildrenMenu extends EditScreenMenu {
    public static final  int MENU_DELETE_ID  = R.id.menu_item_delete;
    public static final  int MENU_DONE_ID    = R.id.menu_item_done;
    public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU            = R.menu.fragment_review_children;

    private MenuItemChildrenRatingAverage mActionItem;
    private DataSetObserver               mObserver;

    public EditChildrenMenu() {
        super(GvDataList.GvType.CHILDREN.getDataString(), GvDataList.GvType.CHILDREN
                .getDataString(), false, true, MENU);
        mActionItem = new MenuItemChildrenRatingAverage();
        mObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                if (getReviewView().isRatingAverage()) {
                    mActionItem.setAverageRating();
                }
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        };
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        ReviewView view = getReviewView();

        view.registerGridDataObserver(mObserver);
        ReviewBuilder.DataBuilder adapter = (ReviewBuilder.DataBuilder) view.getAdapter();
        view.setRatingAverage(adapter.getParentBuilder().isRatingAverage());
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
            GvChildrenList children = (GvChildrenList) getGridData();
            for (GvChildrenList.GvChildReview child : children) {
                rating += child.getRating() / children.size();
            }

            getReviewView().setRating(rating);
        }

        @Override
        public void doAction(MenuItem item) {
            getReviewView().setRatingAverage(true);
            setAverageRating();
        }
    }
}
