/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Performs traverses of review trees. Can set a VisitorReviewNode to visit each node in the
 * traversal.
 * <p/>
 * <p>
 * Can potentially use a number of different search algorithms. Currently Only Depth First
 * Pre-order used.
 * </p>
 */
class TraverserReviewNode {
    private final ReviewNode            mHead;
    private final TraverserSearchMethod mSearchMethod;
    private       VisitorReviewNode     mVisitor;

    TraverserReviewNode(ReviewNode head) {
        mHead = head;
        mSearchMethod = new TraverserSearchDepthFirstPre();
        setVisitor(null);
    }

    void setVisitor(VisitorReviewNode visitor) {
        mVisitor = visitor == null ? new VisitorNull() : visitor;
    }

    void traverse() {
        traverse(mVisitor);
    }

    private void traverse(VisitorReviewNode visitor) {
        mSearchMethod.search(mHead, visitor, 0);
    }
}
