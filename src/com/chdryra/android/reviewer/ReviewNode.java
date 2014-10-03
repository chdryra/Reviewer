/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Node representation of a review. Can add other reviews as children or as a parent allowing a
 * structured representation of how reviews may relate to each other e.g. in a user review with
 * sub-criteria or a meta review.
 * @see ReviewComponent
 * @see ReviewTreeEditable
 */
public interface ReviewNode extends Review {
    public Review getReview();

    public void setParent(ReviewNode parentNode);

    public void addChild(Review child);

    public void addChild(ReviewNode childNode);

    public void removeChild(ReviewNode childNode);

    public void clearChildren();

    /**
     * Collects itself and all descendants into a collection of nodes.
     */
    public RCollectionReview<ReviewNode> flattenTree();

    public RCollectionReview<ReviewNode> getChildren();

    public boolean isRatingIsAverageOfChildren();

    public void setRatingIsAverageOfChildren(boolean ratingIsAverage);

    public void acceptVisitor(VisitorReviewNode visitorReviewNode);
}
