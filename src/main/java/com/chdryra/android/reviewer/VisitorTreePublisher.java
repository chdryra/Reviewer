/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Uses a {@link com.chdryra.android.reviewer.PublisherReviewTree} to publish review trees given
 * a root node.
 */
//TODO reformulate using TraverserReviewNode
class VisitorTreePublisher implements VisitorReviewNode {

    private final PublisherReviewTree  mPublisher;
    private       ReviewNodeExpandable mPublishedNode;

    VisitorTreePublisher(PublisherReviewTree publisher) {
        mPublisher = publisher;
    }

    @Override
    public void visit(ReviewNode reviewNode) {
        Review review = reviewNode.getReview();
        mPublishedNode = FactoryReview.createReviewNodeExpandable(review.publish(mPublisher));

        for (ReviewNode child : reviewNode.getChildren()) {
            VisitorTreePublisher publisher = new VisitorTreePublisher(mPublisher);
            child.acceptVisitor(publisher);
            mPublishedNode.addChild(publisher.getPublishedTree());
        }
    }

    ReviewNode getPublishedTree() {
        return mPublishedNode;
    }

}
