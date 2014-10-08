/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * A non-editable and non-expandable ReviewNode wrapper for a review tree. The published version of
 * ReviewTreeEditable. Has the same RDId as the root node. Getters forward requests to the
 * internal node.
 */
class ReviewTree implements ReviewNode {
    private ReviewNode mNode;

    public ReviewTree(ReviewNode node) {
        mNode = node;
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
    public RCollectionReview<ReviewNode> getChildren() {
        return mNode.getChildren();
    }

    @Override
    public boolean isRatingIsAverageOfChildren() {
        return mNode.isRatingIsAverageOfChildren();
    }

    @Override
    public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
        mNode.setRatingIsAverageOfChildren(ratingIsAverage);
    }

    @Override
    public RCollectionReview<ReviewNode> flattenTree() {
        return mNode.flattenTree();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        mNode.acceptVisitor(visitorReviewNode);
    }

    @Override
    public RDId getId() {
        return mNode.getId();
    }

    @Override
    public RDSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public RDRating getRating() {
        return mNode.getRating();
    }

    @Override
    public ReviewNode getReviewNode() {
        return this;
    }

    @Override
    public Review publish(ReviewTreePublisher publisher) {
        if(!mNode.isPublished())
            mNode = publisher.publish(mNode);

        return this;
    }

    @Override
    public Author getAuthor() {
        return mNode.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public boolean isPublished() {
        return mNode.isPublished();
    }

    @Override
    public RDList<RDComment> getComments() {
        return mNode.getComments();
    }

    @Override
    public boolean hasComments() {
        return mNode.hasComments();
    }

    @Override
    public RDList<RDFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public boolean hasFacts() {
        return mNode.hasFacts();
    }

    @Override
    public RDList<RDImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public boolean hasImages() {
        return mNode.hasImages();
    }

    @Override
    public RDList<RDUrl> getURLs() {
        return mNode.getURLs();
    }

    @Override
    public boolean hasUrls() {
        return mNode.hasUrls();
    }

    @Override
    public RDList<RDLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mNode.hasLocations();
    }
}
