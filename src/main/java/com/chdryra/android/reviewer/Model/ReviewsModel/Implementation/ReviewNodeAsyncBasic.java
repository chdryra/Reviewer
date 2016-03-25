/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeAsync;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeAsyncBasic<T extends ReviewNode> implements ReviewNodeAsync<T> {
    private final ArrayList<NodeObserver> mNodeObservers;
    private T mNode;

    public ReviewNodeAsyncBasic(T initial) {
        mNode = initial;
        mNodeObservers = new ArrayList<>();
        mNode.registerNodeObserver(this);
    }

    protected T getNode(){
        return mNode;
    }

    @Override
    public void updateNode(T node) {
        mNode.unregisterNodeObserver(this);
        mNode = node;
        mNode.registerNodeObserver(this);
        notifyNodeObservers();
    }

    @Override
    public void onNodeChanged() {
        notifyNodeObservers();
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
        if (!mNodeObservers.contains(observer)) mNodeObservers.add(observer);
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
        mNodeObservers.remove(observer);
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
    public IdableList<? extends DataCriterionReview> getCriteria() {
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
        if (!(o instanceof ReviewNodeAsyncBasic)) return false;

        ReviewNodeAsyncBasic<?> that = (ReviewNodeAsyncBasic<?>) o;

        if (!mNodeObservers.equals(that.mNodeObservers)) return false;
        return mNode.equals(that.mNode);

    }

    @Override
    public int hashCode() {
        int result = mNodeObservers.hashCode();
        result = 31 * result + mNode.hashCode();
        return result;
    }

    protected void notifyNodeObservers() {
        for (NodeObserver observer : mNodeObservers) {
            observer.onNodeChanged();
        }
    }
}
