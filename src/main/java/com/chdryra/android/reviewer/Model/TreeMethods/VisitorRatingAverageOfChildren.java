/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.TreeMethods;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * For calculating the average rating of the children of a node.
 */
public class VisitorRatingAverageOfChildren implements VisitorRatingCalculator {
    private float mRating = 0;

    @Override
    public void visit(ReviewNode reviewNode) {
        float total = 0;
        for (ReviewNode child : reviewNode.getChildren()) {
            total += child.getRating().get();
        }

        int num = reviewNode.getChildren().size();
        mRating = num > 0 ? total / num : 0;
    }

    @Override
    public float getRating() {
        return mRating;
    }
}
