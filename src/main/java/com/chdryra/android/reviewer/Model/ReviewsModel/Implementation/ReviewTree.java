/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node that guarantees no
 * more editing or expanding of the node. Has the same {@link MdReviewId} as the wrapped node.
 * <p/>
 * <p>
 * Although a ReviewTree is unchangeable it may still be wrapped by another
 * {@link ReviewTree},
 * thus acting as a fixed, published component of a new review tree with its own {@link MdReviewId}.
 * </p>
 */
public class ReviewTree extends ReviewDynamic implements ReviewNode, ReviewNode.NodeObserver {
    private final ArrayList<NodeObserver> mObservers;
    private ReviewNode mNode;

    public ReviewTree(@NotNull ReviewNode node) {
        mNode = node;
        mObservers = new ArrayList<>();
        node.registerNodeObserver(this);
    }

    @Override
    public void onNodeChanged() {
        notifyNodeChanged();
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public ReviewId getReviewId() {
        return mNode.getReviewId();
    }

    @Override
    public Review getReview() {
        return mNode.getReview();
    }

    @Override
    public ReviewNode getParent() {
        return mNode.getParent();
    }

    @Override
    public ReviewNode getRoot() {
        return mNode.getRoot();
    }

    @Override
    public boolean isExpandable() {
        return mNode.isExpandable();
    }

    @Override
    public ReviewNode expand() {
        return mNode.expand();
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return mNode.getChild(reviewId);
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return mNode.hasChild(reviewId);
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return mNode.getChildren();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(mNode);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mNode.isRatingAverageOfChildren();
    }

    @Override
    public DataSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mNode.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mNode.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mNode.isRatingAverageOfChildren();
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return mNode.getCriteria();
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return mNode.getComments();
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return mNode.getCovers();
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTree)) return false;

        ReviewTree that = (ReviewTree) o;

        return mNode.equals(that.mNode);

    }

    @Override
    public int hashCode() {
        return mNode.hashCode();
    }

    private void notifyNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
        notifyReviewObservers();
    }
}
