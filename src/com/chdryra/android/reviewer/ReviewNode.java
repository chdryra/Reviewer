/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Tree representation of a review.
 *
 * <p>
 *     Can add other reviews as children or as a parent allowing a
 *     structured representation of how reviews may relate to each other, for example a user review
 *     with sub-criteria as children or a meta review with other reviews as children.
 * </p>
 *
 */
interface ReviewNode extends Review {
    Review getReview();

    ReviewNode getParent();

    RCollectionReview<ReviewNode> getChildren();

    boolean isRatingIsAverageOfChildren();

    void setRatingIsAverageOfChildren(boolean ratingIsAverage);

    /**
     * Collects itself and all descendants into a collection of nodes.
     */
    RCollectionReview<ReviewNode> flattenTree();

    /**
     * For operations to be carried out on the tree.
     * @param visitorReviewNode
     */
    void acceptVisitor(VisitorReviewNode visitorReviewNode);
}
