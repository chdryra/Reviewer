/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.database.DataSetObserver;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MenuEditChildren extends MenuDeleteDone {
    public static final  int MENU_DELETE_ID  = R.id.menu_item_delete;
    public static final  int MENU_DONE_ID    = R.id.menu_item_done;
    public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU            = R.menu.fragment_review_children;

    private MenuItemChildrenRatingAverage mActionItem;

    public MenuEditChildren(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.COMMENTS, false, true, MENU);
        mActionItem = new MenuItemChildrenRatingAverage(this);
    }

    @Override
    public void onSetReviewView() {
        super.onSetReviewView();
        getViewReview().registerGridDataObserver(getObserver());
    }

    @Override
    protected void addMenuItems() {
        addDefaultDeleteActionItem(MENU_DELETE_ID);
        addDefaultDoneActionItem(MENU_DONE_ID);
        addMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
    }

    private DataSetObserver getObserver() {
        return new DataSetObserver() {
            @Override
            public void onChanged() {
                mActionItem.setAverageRating();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        };
    }
}
