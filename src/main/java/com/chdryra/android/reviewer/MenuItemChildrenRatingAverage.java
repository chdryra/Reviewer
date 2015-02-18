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
    private boolean                     mUseBuilder;
    private ReviewBuilder               mBuilder;

    public MenuItemChildrenRatingAverage(ViewReviewAction.MenuAction action, boolean useBuilder) {
        mAction = action;
        mUseBuilder = useBuilder;
        if (!mUseBuilder && action.getDataType() != GvDataList.GvType.CHILDREN) {
            throw new RuntimeException("Action data should be GvChildrenList");
        }

        try {
            mBuilder = (ReviewBuilder) mAction.getAdapter();
        } catch (ClassCastException e) {
            throw new RuntimeException("Adapter should be ReviewBuilder");
        }
    }

    public void setAverageRating() {
        GvChildrenList children = mUseBuilder ?
                (GvChildrenList) mBuilder.getData(GvDataList.GvType.CHILDREN)
                : (GvChildrenList) mAction.getData();

        float rating = 0;
        for (GvChildrenList.GvChildReview child : children) {
            rating += child.getRating() / children.size();
        }

        mAction.getViewReview().setRating(rating);
        //if(mUseBuilder) mBuilder.setRating(rating);
    }

    @Override
    public void doAction(MenuItem item) {
        mBuilder.setRatingIsAverage(true);
        setAverageRating();
    }
}
