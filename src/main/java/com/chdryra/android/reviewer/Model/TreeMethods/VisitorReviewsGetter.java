/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.TreeMethods;

import com.chdryra.android.reviewer.Model.ReviewData.MdIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewsGetter implements VisitorReviewNode {
    MdIdableList<Review> mReviews = new MdIdableList<>();

    //Static methods
    public static MdIdableList<Review> flatten(ReviewNode node) {
        VisitorReviewsGetter flattener = new VisitorReviewsGetter();
        node.acceptVisitor(flattener);
        return flattener.getReviews();
    }

    //public methods
    public MdIdableList<Review> getReviews() {
        return mReviews;
    }

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        MdIdableList<ReviewNode> children = node.getChildren();
        ReviewNode expanded = node.expand();
        if (children.size() == 0) {
            if (expanded.getReview() == node.getReview()) {
                mReviews.add(node.getReview());
            } else {
                expanded.acceptVisitor(this);
            }
        } else {
            for (ReviewNode child : children) {
                child.acceptVisitor(this);
            }
        }
    }
}
