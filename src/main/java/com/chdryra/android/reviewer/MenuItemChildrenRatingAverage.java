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
    private boolean                     mFromController;
    private ReviewBuilder               mBuilder;

    public MenuItemChildrenRatingAverage(ViewReviewAction.MenuAction action,
            boolean fromController) {
        mAction = action;
        mFromController = fromController;
        if (!mFromController && action.getDataType() != GvDataList.GvType.CHILDREN) {
            throw new RuntimeException("Action data should be GvChildrenList");
        }

        try {
            mBuilder = (ReviewBuilder) mAction.getAdapter();
        } catch (ClassCastException e) {
            throw new RuntimeException("Controller should be ControllerReviewBuilder");
        }
    }

    public void setAverageRating() {
        float rating = 0;
        GvChildrenList children;
        if (mFromController) {
            children = (GvChildrenList) mBuilder.getData(GvDataList.GvType.CHILDREN);
        } else {
            children = (GvChildrenList) mAction.getData();
        }

        int numChildren = children.size();
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
        mBuilder.setRatingIsAverage(true);
        setAverageRating();
    }
}
