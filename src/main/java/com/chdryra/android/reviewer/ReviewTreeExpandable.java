/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Primary implementation of {@link ReviewTreeExpandable}.
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
class ReviewTreeExpandable implements ReviewNode {
    private final ReviewId mId;

    private final Review                                  mReview;
    private final RCollectionReview<ReviewNode> mChildren;
    private       ReviewTreeExpandable          mParent;

    private boolean mRatingIsAverage = false;

    ReviewTreeExpandable(Review root, boolean ratingIsAverage, boolean uniqueId) {
        mId = uniqueId ? ReviewId.generateId() : root.getId();
        mReview = root;
        mChildren = new RCollectionReview<>();
        mRatingIsAverage = ratingIsAverage;
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

    public void setParent(ReviewTreeExpandable parentNode) {
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
    public RCollectionReview<ReviewNode> getChildren() {
        return mChildren;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        visitorReviewNode.visit(this);
    }

    public void addChild(ReviewTreeExpandable childNode) {
        if (mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.add(childNode);
        childNode.setParent(this);
    }

    public void removeChild(ReviewTreeExpandable childNode) {
        if (!mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.remove(childNode.getId());
        childNode.setParent(null);
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
        return mRatingIsAverage ? new MdRating(RatingAverager.average(this),
                this) : mReview.getRating();
    }

    @Override
    public ReviewNode getReviewNode() {
        return this;
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

        ReviewTreeExpandable objNode = (ReviewTreeExpandable) obj;
        return mId.equals(objNode.mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public ReviewTree createTree() {
        return new ReviewTree(this);
    }
}
