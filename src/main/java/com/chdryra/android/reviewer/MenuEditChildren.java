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
    private DataSetObserver               mObserver;

    public MenuEditChildren(ReviewBuilder controller) {
        super(controller, GvDataList.GvType.CHILDREN, false, true, MENU);
        mActionItem = new MenuItemChildrenRatingAverage(this, false);
        mObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                if (getBuilder().isRatingAverage()) {
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
    public void onSetViewReview() {
        super.onSetViewReview();
        getViewReview().registerGridDataObserver(mObserver);
    }

    @Override
    public void onUnsetViewReview() {
        super.onUnsetViewReview();
        getViewReview().unregisterGridDataObserver(mObserver);
    }

    @Override
    protected void addMenuItems() {
        addDefaultDeleteActionItem(MENU_DELETE_ID);
        addDefaultDoneActionItem(MENU_DONE_ID);
        addMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
    }
}
