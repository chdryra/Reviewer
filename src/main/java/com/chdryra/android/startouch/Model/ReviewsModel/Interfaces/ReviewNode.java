/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Tree representation of a review.
 * <p/>
 * <p>
 * Can have other reviews as children or as a parent allowing a structured representation of how
 * reviews may relate to each other, for example a user review with sub-criteria as children
 * or a meta review with other reviews as children.
 * </p>
 */
public interface ReviewNode extends NodeData {
    interface NodeObserver {
        void onChildAdded(ReviewNode child);

        void onChildRemoved(ReviewNode child);

        void onNodeChanged();

        void onTreeChanged();
    }

    void registerObserver(NodeObserver observer);

    void unregisterObserver(NodeObserver observer);

    @Nullable
    ReviewNode getParent();

    ReviewNode getRoot();

    IdableList<ReviewNode> getChildren();

    @Nullable
    ReviewNode getChild(ReviewId reviewId);

    boolean hasChild(ReviewId reviewId);

    void acceptVisitor(VisitorReviewNode visitor);

    boolean isLeaf();

    @Nullable
    ReviewReference getReference();
}
