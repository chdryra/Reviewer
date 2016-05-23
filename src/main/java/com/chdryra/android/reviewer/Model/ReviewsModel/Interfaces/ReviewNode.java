/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

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
    interface NodeObserver {
        void onNodeChanged();
    }

    void registerNodeObserver(NodeObserver observer);

    void unregisterNodeObserver(NodeObserver observer);

    Review getReview();

    @Nullable
    ReviewNode getParent();

    ReviewNode getRoot();

    boolean isExpandable();

    ReviewNode expand();

    IdableList<ReviewNode> getChildren();

    @Nullable
    ReviewNode getChild(ReviewId reviewId);

    boolean hasChild(ReviewId reviewId);

    void acceptVisitor(VisitorReviewNode visitor);

    boolean isRatingAverageOfChildren();
}
