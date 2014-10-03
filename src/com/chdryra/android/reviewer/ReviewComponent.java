/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Primary implementation of ReviewNode.
 * Wraps a Review object in a node structure with potential children and a parent.
 * Note: this is not necessarily the same node internal to the Review and returned by its
 * getReviewNode() method. A Review may decide to represent itself with its own internal tree
 * structure.
 */
public class ReviewComponent implements ReviewNode {
    private final RDId mId;

    private final Review                mReview;
    private final RCollectionReview<ReviewNode> mChildren;
    private       ReviewNode            mParent;
    private boolean mRatingIsAverage = false;

    public ReviewComponent(Review review) {
        mId = RDId.generateId();
        mReview = review;
        mChildren = new RCollectionReview<ReviewNode>();
    }

    //ReviewNode methods
    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public void setParent(ReviewNode parentNode) {
        if (mParent != null && parentNode != null && mParent.getId().equals(parentNode.getId())) {
            return;
        }

        if(mParent != null) {
            mParent.removeChild(this);
        }

        mParent = parentNode;
        if (mParent != null) {
            mParent.addChild(this);
        }
    }

    @Override
    public void addChild(Review child) {
        addChild(new ReviewComponent(child));
    }

    @Override
    public void addChild(ReviewNode childNode) {
        if (mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.add(childNode);
        childNode.setParent(this);
    }

    @Override
    public void removeChild(ReviewNode childNode) {
        if (!mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.remove(childNode.getId());
        childNode.setParent(null);
    }

    @Override
    public RCollectionReview<ReviewNode> getChildren() {
        return mChildren;
    }

    @Override
    public void clearChildren() {
        RCollectionReview<ReviewNode> children = new RCollectionReview<ReviewNode>();
        children.add(getChildren());
        for (ReviewNode child : children) {
            removeChild(child);
        }
    }

    @Override
    public RCollectionReview<ReviewNode> flattenTree() {
        TraverserReviewNode traverser = new TraverserReviewNode(this);
        VisitorNodeCollector collector = new VisitorNodeCollector();
        traverser.setVisitor(collector);
        traverser.traverse();

        return collector.get();
    }

    @Override
    public boolean isRatingIsAverageOfChildren() {
        return mRatingIsAverage;
    }

    @Override
    public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
        mRatingIsAverage = ratingIsAverage;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        visitorReviewNode.visit(this);
    }

    @Override
    public RDId getId() {
        return mId;
    }

    @Override
    public RDSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public RDRating getRating() {
        return isRatingIsAverageOfChildren() ? getAverageRatingOfChildren() : mReview.getRating();
    }

    //ReviewEditable methods
    @Override
    public ReviewNode getReviewNode() {
        return this;
    }

    @Override
    public ReviewNode publish(ReviewTreePublisher publisher) {
        return publisher.publish(this);
    }

    @Override
    public Author getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public boolean isPublished() {
        return mReview.isPublished();
    }

    @Override
    public RDList<RDComment> getComments() {
        return mReview.getComments();
    }

    @Override
    public boolean hasComments() {
        return mReview.hasComments();
    }

    @Override
    public RDList<RDFact> getFacts() {
        return mReview.getFacts();
    }

    @Override
    public boolean hasFacts() {
        return mReview.hasFacts();
    }

    @Override
    public RDList<RDImage> getImages() {
        return mReview.getImages();
    }

    @Override
    public boolean hasImages() {
        return mReview.hasImages();
    }

    @Override
    public RDList<RDUrl> getURLs() {
        return mReview.getURLs();
    }

    @Override
    public boolean hasURLs() {
        return mReview.hasURLs();
    }

    @Override
    public RDList<RDLocation> getLocations() {
        return mReview.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mReview.hasLocations();
    }

    private RDRating getAverageRatingOfChildren() {
        VisitorRatingCalculator visitor = new VisitorRatingAverageOfChildren();
        acceptVisitor(visitor);
        return new RDRating(visitor.getRating(), this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        ReviewComponent objNode = (ReviewComponent) obj;
        return mId.equals(objNode.mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }


}
