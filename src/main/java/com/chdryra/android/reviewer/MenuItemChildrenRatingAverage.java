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
        GvChildrenList children = (GvChildrenList) mAction.getData();
        int numChildren = children.size();
        float rating = 0;
        for (GvChildrenList.GvChildReview child : children) {
            rating += child.getRating();
        }
        if (numChildren > 0) {
            rating /= numChildren;
        }

        mAction.getViewReview().setRating(rating);
    }

    @Override
    public void doAction(MenuItem item) {
        mAction.getController().getReviewNode().setReviewRatingAverage(true);
        setAverageRating();
    }
}
