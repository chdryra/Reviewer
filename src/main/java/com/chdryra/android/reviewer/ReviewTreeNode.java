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
 * Creates a new unique {@link ReviewId} if required so can represent a new review structure even
 * though it wraps an existing review.
 * </p>
 * <p/>
 * <p>
 * Wraps a {@link Review} object in a node structure with potential children and a parent.
 * Note: this is not necessarily the same node internal to the wrapped {@link Review} and
 * returned by its {@link Review#getReviewNode()} method. A Review may decide to represent
 * itself with its own internal tree structure which will share the same {@link ReviewId} as the
 * review.
 * </p>
 */
public class ReviewTreeNode implements ReviewNode {
    private final ReviewId mId;

    private final Review                        mReview;
    private final RCollectionReview<ReviewNode> mChildren;
    private       ReviewTreeNode                mParent;

    private boolean mRatingIsAverage = false;

    public ReviewTreeNode(Review root, boolean ratingIsAverage, boolean uniqueId) {
        mId = uniqueId ? ReviewId.generateId() : root.getId();
        mReview = root;
        mChildren = new RCollectionReview<>();
        mParent = null;
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

    public void setParent(ReviewTreeNode parentNode) {
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
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    public void addChild(ReviewTreeNode childNode) {
        if (mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.add(childNode);
        childNode.setParent(this);
    }

    public void removeChild(ReviewTreeNode childNode) {
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
        MdRating rating;
        if (mRatingIsAverage) {
            VisitorRatingCalculator visitor = new VisitorRatingAverageOfChildren();
            acceptVisitor(visitor);
            rating = new MdRating(visitor.getRating(), this);
        } else {
            rating = mReview.getRating();
        }

        return rating;
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

        ReviewTreeNode objNode = (ReviewTreeNode) obj;
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
