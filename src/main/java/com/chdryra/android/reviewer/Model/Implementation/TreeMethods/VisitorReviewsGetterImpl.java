/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdIdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewsGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewsGetterImpl implements VisitorReviewsGetter {
    IdableCollection<Review> mReviews;

    public VisitorReviewsGetterImpl() {
    }

    //public methods
    @Override
    public IdableCollection<Review> getReviews() {
        return mReviews;
    }

    //Overridden
    @Override
    public void visit(ReviewNode node) {
        if(mReviews == null) newList(node);

        if (isLeafNode(node)) {
            mReviews.add(node.getReview());
        } else {
            expandAndVisit(node);
            getDescendentReviews(node.getChildren());
        }
    }

    private void expandAndVisit(ReviewNode node) {
        node.expand().acceptVisitor(this);
    }

    private boolean isLeafNode(ReviewNode node) {
        return node.getChildren().size() == 0 && node.expand().getReview() == node.getReview();
    }

    private void newList(ReviewNode node) {
        MdReviewId id = new MdReviewId(node.getReviewId());
        mReviews = new MdIdableList<>(id);
    }

    private void getDescendentReviews(IdableList<ReviewNode> children) {
        for (ReviewNode child : children) {
            child.acceptVisitor(this);
        }
    }
}
