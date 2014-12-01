/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * For calculating the "average of averages" for the descendants of a node.
 */
//TODO reformulate using the TraverserReviewNode
class VisitorRatingAverageOverDescendents implements VisitorRatingCalculator {
    private float mRatingTotal   = 0;
    private int   mNumberRatings = 0;

    private VisitorRatingAverageOverDescendents() {
    }

    @Override
    public void visit(ReviewNode reviewNode) {
        if (reviewNode.getChildren().size() == 0) {
            mRatingTotal += reviewNode.getRating().get();
            mNumberRatings = 1;
        } else {
            for (ReviewNode child : reviewNode.getChildren()) {
                VisitorRatingAverageOverDescendents averager = new
                        VisitorRatingAverageOverDescendents();
                child.acceptVisitor(averager);
                mRatingTotal += averager.getRating();
                mNumberRatings++;
            }
        }
    }

    @Override
    public float getRating() {
        if (mNumberRatings > 0) {
            return mRatingTotal / mNumberRatings;
        } else {
            return 0;
        }
    }
}
