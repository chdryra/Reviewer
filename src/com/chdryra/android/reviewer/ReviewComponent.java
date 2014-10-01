/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

public class ReviewComponent implements ReviewNode {
    private final RDId mID;

    private final Review                mReview;
    private final RCollectionReviewNode mChildren;
    private       ReviewNode            mParent;
    private boolean mRatingIsAverage = false;

    public ReviewComponent(Review review) {
        mID = RDId.generateId();
        mReview = review;
        mChildren = new RCollectionReviewNode();
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
        if (mChildren.containsID(childNode.getId())) {
            return;
        }
        mChildren.add(childNode);
        childNode.setParent(this);
    }

    @Override
    public RCollectionReviewNode getChildren() {
        return mChildren;
    }

    @Override
    public void clearChildren() {
        RCollectionReviewNode children = new RCollectionReviewNode();
        children.add(getChildren());
        for (ReviewNode child : children) {
            removeChild(child.getId());
        }
    }

    @Override
    public RCollectionReviewNode flatten() {
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

    void removeChild(RDId id) {
        ReviewNode child = mChildren.get(id);
        child.setParent(null);
        mChildren.remove(child.getId());
    }

    @Override
    public RDId getId() {
        return mReview.getId();
    }

    @Override
    public RDSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public RDRating getRating() {
        return isRatingIsAverageOfChildren() ? getAverageRatingOfChildren() : mReview.getRating();
    }

    @Override
    public ReviewTagCollection getTags() {
        return mReview.getTags();
    }

    //ReviewEditable methods
    @Override
    public ReviewNode getReviewNode() {
        return this;
    }

    @Override
    public ReviewNode publish(ReviewTreePublisher publisher) {
        return mReview.publish(publisher);
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
        return mID.equals(objNode.mID);
    }

    @Override
    public int hashCode() {
        return mID.hashCode();
    }


}
