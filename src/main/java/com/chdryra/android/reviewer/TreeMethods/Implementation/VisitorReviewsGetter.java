/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.TreeMethods.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewsGetter implements VisitorReviewNode {
    IdableCollection<Review> mReviews = new MdIdableCollection<>();

    //public methods
    public IdableCollection<Review> getReviews() {
        return mReviews;
    }

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        IdableList<ReviewNode> children = node.getChildren();
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
