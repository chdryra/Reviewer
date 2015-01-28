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
public class MenuItemChildrenRatingAverage extends ViewReviewAction.MenuAction.MenuActionItem {
    private ViewReviewAction.MenuAction mAction;

    public MenuItemChildrenRatingAverage(ViewReviewAction.MenuAction action) {
        mAction = action;
    }

    public void setAverageRating() {
        ControllerReviewEditable controller = (ControllerReviewEditable) mAction.getController();
        controller.getReviewNode().setReviewRatingAverage(true);
        mAction.getViewReview().setRating(controller.getRating());
    }

    @Override
    public void doAction(MenuItem item) {
        setAverageRating();
    }
}
