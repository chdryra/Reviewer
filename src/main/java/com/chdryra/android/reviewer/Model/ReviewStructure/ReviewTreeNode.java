/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdRating;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.TreeMethods.ReviewTreeComparer;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorRatingCalculator;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewNode;
import com.chdryra.android.reviewer.Model.UserData.Author;

/**
 * Creates a new unique {@link ReviewId} if required so can represent a new review structure even
 * though it wraps an existing review.
 * </p>
 * <p/>
 * <p>
 * Wraps a {@link Review} object in a node structure with potential children and a parent.
 * </p>
 */
public class ReviewTreeNode implements ReviewNode {
    private final ReviewId mId;

    private final Review                        mReview;
    private final ReviewIdableList<ReviewNode> mChildren;
    private       ReviewTreeNode               mParent;

    private boolean mRatingIsAverage = false;

    public ReviewTreeNode(Review root, boolean ratingIsAverage, ReviewId nodeId) {
        mId = nodeId;
        mReview = root;
        mChildren = new ReviewIdableList<>();
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

    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public ReviewIdableList<ReviewNode> getChildren() {
        return mChildren;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mRatingIsAverage;
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

    public void addChild(ReviewTreeNode childNode) {
        if (mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.add(childNode);
        childNode.setParent(this);
    }

    public void removeChild(ReviewId reviewId) {
        removeChild((ReviewTreeNode) mChildren.get(reviewId));
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
            rating = new MdRating(visitor.getRating(), mId);
        } else {
            rating = mReview.getRating();
        }

        return rating;
    }

    @Override
    public Author getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public PublishDate getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public MdCommentList getComments() {
        MdCommentList comments = new MdCommentList(mId);
        comments.addList(mReview.getComments());
        for (ReviewNode child : mChildren) {
            comments.addList(child.getComments());
        }

        return comments;
    }

    @Override
    public MdFactList getFacts() {
        MdFactList facts = new MdFactList(mId);
        facts.addList(mReview.getFacts());
        for (ReviewNode child : mChildren) {
            facts.addList(child.getFacts());
        }

        return facts;
    }

    @Override
    public MdImageList getImages() {
        MdImageList images = new MdImageList(mId);
        images.addList(mReview.getImages());
        for (ReviewNode child : mChildren) {
            images.addList(child.getImages());
        }

        return images;
    }

    @Override
    public MdLocationList getLocations() {
        MdLocationList locations = new MdLocationList(mId);
        locations.addList(mReview.getLocations());
        for (ReviewNode child : mChildren) {
            locations.addList(child.getLocations());
        }

        return locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNode)) return false;

        ReviewNode that = (ReviewNode) o;
        return ReviewTreeComparer.compareNodes(this, that);
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mReview.hashCode();
        result = 31 * result + mChildren.hashCode();
        result = 31 * result + (mParent != null ? mParent.hashCode() : 0);
        result = 31 * result + (mRatingIsAverage ? 1 : 0);
        return result;
    }

    public ReviewNode createTree() {
        return new ReviewTree(this);
    }

    private void removeChild(ReviewTreeNode childNode) {
        if (!mChildren.containsId(childNode.getId())) {
            return;
        }
        mChildren.remove(childNode.getId());
        childNode.setParent(null);
    }
}
