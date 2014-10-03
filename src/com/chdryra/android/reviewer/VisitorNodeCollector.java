/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class VisitorNodeCollector implements VisitorReviewNode {

    private final RCollectionReview<ReviewNode> mNodes = new RCollectionReview<ReviewNode>();

    @Override
    public void visit(ReviewNode reviewNode) {
        mNodes.add(reviewNode);
    }

    public RCollectionReview<ReviewNode> get() {
        return mNodes;
    }
}
