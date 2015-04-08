/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model;

/**
 * Tree representation of a review.
 * <p/>
 * <p>
 * Can add other reviews as children or as a parent allowing a structured representation of how
 * reviews may relate to each other, for example a user review with sub-criteria as children
 * or a meta review with other reviews as children.
 * </p>
 */
public interface ReviewNode extends Review {
    public Review getReview();

    public ReviewNode getParent();

    public RCollectionReview<ReviewNode> getChildren();

    public void acceptVisitor(VisitorReviewNode visitor);

    public boolean isRatingAverageOfChildren();
}