/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.TreeMethods;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdIdableCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewsGetter implements VisitorReviewNode {
    MdIdableCollection<Review> mReviews = new MdIdableCollection<>();

    //Static methods
    public static MdIdableCollection<Review> flatten(ReviewNode node) {
        VisitorReviewsGetter flattener = new VisitorReviewsGetter();
        node.acceptVisitor(flattener);
        return flattener.getReviews();
    }

    //public methods
    public MdIdableCollection<Review> getReviews() {
        return mReviews;
    }

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        IdableCollection<ReviewNode> children = node.getChildren();
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
