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
public class MenuEditChildren extends MenuDeleteDone {
    public static final  int MENU_DELETE_ID  = R.id.menu_item_delete;
    public static final  int MENU_DONE_ID    = R.id.menu_item_done;
    public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
    private static final int MENU            = R.menu.fragment_review_children;

    public MenuEditChildren(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.COMMENTS, false, true, MENU);
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
        addMenuActionItem(getTotalRatingIsAverageAction(), MENU_AVERAGE_ID, false);
    }

    private void setRating() {
        ControllerReviewEditable controller = (ControllerReviewEditable) getController();
        if (controller.getReviewNode().isReviewRatingAverage()) setAverageRating();
    }

    private void setRating(boolean isAverage) {
        ControllerReviewEditable controller = (ControllerReviewEditable) getController();
        controller.getReviewNode().setReviewRatingAverage(isAverage);
        setRating();
    }

    private void setAverageRating() {
        GvChildrenList children = (GvChildrenList) getData();
        float rating = 0;
        for (GvChildrenList.GvChildReview child : children) {
            rating += child.getRating() / children.size();
        }

        getViewReview().setRating(rating);
    }

    private ViewReviewAction.MenuAction.MenuActionItem getTotalRatingIsAverageAction() {
        return new ViewReviewAction.MenuAction.MenuActionItem() {
            @Override
            public void doAction(MenuItem item) {
                setRating(true);
            }
        };
    }

    private DataSetObserver getObserver() {
        return new DataSetObserver() {
            @Override
            public void onChanged() {
                setRating();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        };
    }
}
