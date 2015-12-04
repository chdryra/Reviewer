/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataRating;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorRatingCalculator;

/**
 * For calculating the average rating of the children of a node.
 */
public class VisitorRatingAverager implements VisitorRatingCalculator {
    private float mRating = 0;
    private int mWeight = 0;

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        DataRating rating = node.getRating();
        mWeight += rating.getRatingWeight();
        mRating += rating.getRating() * rating.getRatingWeight();
    }

    @Override
    public float getRating() {
        return mWeight > 0 ? mRating / mWeight : mRating;
    }

    @Override
    public int getWeight() {
        return mWeight;
    }
}
