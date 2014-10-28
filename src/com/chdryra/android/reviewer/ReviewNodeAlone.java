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
 * Wrapper for a single review without children or a parent. Has same {@link RDId} as the review it
 * wraps.
 * Used primarily as the tree representation for {@link ReviewUserEditable}.
 */
class ReviewNodeAlone implements ReviewNode {
    private final Review mReview;
    private final RCollectionReview<ReviewNode> mChildren = new RCollectionReview<ReviewNode>();

    ReviewNodeAlone(Review review) {
        mReview = review;
    }

    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public ReviewNode getParent() {
        return null;
    }

    @Override
    public RCollectionReview<ReviewNode> getChildren() {
        return mChildren;
    }

    @Override
    public boolean isRatingIsAverageOfChildren() {
        return false;
    }

    @Override
    public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
    }

    @Override
    public RCollectionReview<ReviewNode> flattenTree() {
        RCollectionReview<ReviewNode> nodes = new RCollectionReview<ReviewNode>();
        nodes.add(this);

        return nodes;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        visitorReviewNode.visit(this);
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
        return mReview.getRating();
    }

    @Override
    public ReviewNode getReviewNode() {
        return mReview.getReviewNode();
    }

    @Override
    public Review publish(PublisherReviewTree publisher) {
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
    public boolean hasUrls() {
        return mReview.hasUrls();
    }

    @Override
    public RDList<RDLocation> getLocations() {
        return mReview.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mReview.hasLocations();
    }
}
