/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.TreeMethods;

import com.chdryra.android.reviewer.Interfaces.Data.DataRating;
import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;

/**
 * For calculating the average rating of the children of a node.
 */
public class VisitorRatingAverageOfChildren implements VisitorRatingCalculator {
    private float mRating = 0;
    private int mWeight = 0;

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        IdableCollection<ReviewNode> children = node.getChildren();
        if (children.size() == 0) mWeight = 1;
        for (ReviewNode child : children) {
            DataRating rating = child.getRating();
            int weight = rating.getWeight();
            mRating += rating.getRating() * weight;
            mWeight += rating.getWeight();
        }

        mRating /= mWeight;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public int getWeight() {
        return mWeight;
    }
}
