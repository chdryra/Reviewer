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
 * Primary implementation of {@link ReviewNodeExpandable}.
 * <p/>
 * <p>
 * Creates a new unique {@link ReviewId} so represents a new review structure even though it
 * wraps an
 * existing review. Generally used for reviews that only make sense when considering the tree
 * as a whole, for example reviews with rated sub-criteria, meta-reviews etc.
 * </p>
 * <p/>
 * <p>
 * Wraps a {@link Review} object in a node structure with potential children and a parent.
 * Note: this is not necessarily the same node internal to the wrapped {@link Review} and
 * returned by
 * its {@link Review#getReviewNode()} method. A Review may decide to represent
 * itself with its own internal tree structure which will share the same {@link ReviewId} as the
 * review.
 * </p>
 */
class ReviewNodeExpandableImpl implements ReviewNodeExpandable {
    private final ReviewId mId;

    private final Review                                  mReview;
    private final RCollectionReview<ReviewNodeExpandable> mChildren;
    private       ReviewNodeExpandable                    mParent;

    private boolean mRatingIsAverage = false;

    ReviewNodeExpandableImpl(Review review) {
        mId = ReviewId.generateId();
        mReview = review;
        mChildren = new RCollectionReview<>();
    }

    //ReviewNode methods
    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public ReviewNode getParent() {
        return mParent;
    }

    @Override
    public void setParent(ReviewNodeExpandable parentNode) {
        if (mParent != null && parentNode != null && mParent.getId().equals(parentNode.getId())) {
            return;
        }

        if (mParent != null) {
            mParent.removeChild(this);
        }

        mParent = parentNode;
        if (mParent != null) {
            mParent.addChild(this);
        }
    }

    @Override
    public ReviewNodeExpandable addChild(Review child) {
        ReviewNodeExpandable node = new ReviewNodeExpandableImpl(child);
        addChild(node);
        return node;
    }

    @Override
    public void addChild(ReviewNodeExpandable childNode) {
        if (mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.add(childNode);
        childNode.setParent(this);
    }

    @Override
    public void removeChild(ReviewNodeExpandable childNode) {
        if (!mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.remove(childNode.getId());
        childNode.setParent(null);
    }

    @Override
    public void clearChildren() {
        RCollectionReview<ReviewNodeExpandable> children = new
                RCollectionReview<ReviewNodeExpandable>();
        children.add(mChildren);
        for (ReviewNodeExpandable child : children) {
            removeChild(child);
        }
    }

    @Override
    public RCollectionReview<ReviewNode> getChildren() {
        RCollectionReview<ReviewNode> children = new RCollectionReview<ReviewNode>();
        for (ReviewNodeExpandable child : mChildren) {
            children.add(child);
        }

        return children;
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
    public RCollectionReview<ReviewNode> flattenTree() {
        TraverserReviewNode traverser = new TraverserReviewNode(this);
        VisitorNodeCollector collector = new VisitorNodeCollector();
        traverser.setVisitor(collector);
        traverser.traverse();

        return collector.get();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        visitorReviewNode.visit(this);
    }

    //Review methods
    @Override
    public ReviewId getId() {
        return mId;
    }

    @Override
    public MdSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public MdRating getRating() {
        return isRatingIsAverageOfChildren() ? getAverageRatingOfChildren() : mReview.getRating();
    }

    @Override
    public ReviewNode getReviewNode() {
        return this;
    }

    @Override
    public ReviewNode publish(PublisherReviewTree publisher) {
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
        for (ReviewNode node : getChildren()) {
            if (!node.isPublished()) {
                return false;
            }
        }

        return mReview.isPublished();
    }

    @Override
    public MdCommentList getComments() {
        return mReview.getComments();
    }

    @Override
    public boolean hasComments() {
        return mReview.hasComments();
    }

    @Override
    public MdFactList getFacts() {
        return mReview.getFacts();
    }

    @Override
    public boolean hasFacts() {
        return mReview.hasFacts();
    }

    @Override
    public MdImageList getImages() {
        return mReview.getImages();
    }

    @Override
    public boolean hasImages() {
        return mReview.hasImages();
    }

    @Override
    public MdUrlList getUrls() {
        return mReview.getUrls();
    }

    @Override
    public boolean hasUrls() {
        return mReview.hasUrls();
    }

    @Override
    public MdLocationList getLocations() {
        return mReview.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mReview.hasLocations();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        ReviewNodeExpandableImpl objNode = (ReviewNodeExpandableImpl) obj;
        return mId.equals(objNode.mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    private MdRating getAverageRatingOfChildren() {
        VisitorRatingCalculator visitor = new VisitorRatingAverageOfChildren();
        acceptVisitor(visitor);
        return new MdRating(visitor.getRating(), this);
    }
}
