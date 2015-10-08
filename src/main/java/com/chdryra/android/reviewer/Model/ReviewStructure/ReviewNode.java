/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewNode;

/**
 * Tree representation of a review.
 * <p/>
 * <p>
 * Can have other reviews as children or as a parent allowing a structured representation of how
 * reviews may relate to each other, for example a user review with sub-criteria as children
 * or a meta review with other reviews as children.
 * </p>
 */
public interface ReviewNode extends Review {
    //abstract methods
    //abstract
    Review getReview();

    ReviewNode getParent();

    ReviewNode getRoot();

    ReviewNode expand();

    IdableList<ReviewNode> getChildren();

    void acceptVisitor(VisitorReviewNode visitor);

    boolean isRatingAverageOfChildren();
}
