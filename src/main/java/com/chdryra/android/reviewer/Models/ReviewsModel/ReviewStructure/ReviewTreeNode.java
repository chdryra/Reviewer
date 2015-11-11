/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthorReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataDateReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataRating;
import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdRating;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.TreeMethods.ReviewTreeComparer;
import com.chdryra.android.reviewer.TreeMethods.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.TreeMethods.VisitorRatingCalculator;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewNode;

/**
 * Creates a new unique {@link MdReviewId} if required so can represent a new review structure even
 * though it wraps an existing review.
 * </p>
 * <p/>
 * <p>
 * Wraps a {@link Review} object in a node structure with potential children and a parent.
 * </p>
 */
public class ReviewTreeNode implements ReviewNodeComponent {
    private final MdReviewId mId;

    private final Review mReview;
    private final TreeDataGetter mGetter;
    private final MdIdableList<ReviewNode> mChildren;
    private ReviewNodeComponent mParent;
    private boolean mRatingIsAverage = false;

    //Constructors
    public ReviewTreeNode(Review review,
                          boolean ratingIsAverage,
                          MdReviewId nodeId,
                          TreeDataGetter treeDataGetter) {
        mId = nodeId;
        mReview = review;
        mChildren = new MdIdableList<>(nodeId);
        mParent = null;
        mRatingIsAverage = ratingIsAverage;
        mGetter = treeDataGetter;
        mGetter.setRoot(this);
    }

    private void removeChild(ReviewNodeComponent childNode) {
        if (!mChildren.containsId(childNode.getReviewId())) {
            return;
        }
        mChildren.remove(childNode.getReviewId());
        childNode.setParent(null);
    }

    //Overridden
    //ReviewNode methods
    
    @Override
    public String getReviewId() {
        return mId.toString();
    }

    @Override
    public boolean addChild(ReviewNodeComponent childNode) {
        if (mChildren.containsId(childNode.getReviewId())) {
            return false;
        }
        mChildren.add(childNode);
        childNode.setParent(this);

        return true;
    }

    @Override
    public void removeChild(String reviewId) {
        removeChild((ReviewTreeNode) mChildren.get(reviewId));
    }

    @Override
    public ReviewNode makeTree() {
        return new ReviewTree(this);
    }

    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public ReviewNode getParent() {
        return mParent;
    }

    @Override
    public void setParent(ReviewNodeComponent parentNode) {
        if (mParent != null && parentNode != null && mParent.getReviewId().equals(parentNode.getReviewId())) {
            return;
        }

        if (mParent != null) mParent.removeChild(mId.getReviewId());
        mParent = parentNode;
        if (mParent != null) mParent.addChild(this);
    }

    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public ReviewNode expand() {
        return getReview().getTreeRepresentation();
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
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

    @Override
    public DataSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public DataRating getRating() {
        DataRating rating;
        if (mRatingIsAverage) {
            VisitorRatingCalculator visitor = new VisitorRatingAverageOfChildren();
            acceptVisitor(visitor);
            rating = new MdRating(mId, visitor.getRating(), visitor.getWeight());
        } else {
            rating = mReview.getRating();
        }

        return rating;
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return !mRatingIsAverage && mReview.isRatingAverageOfCriteria();
    }

    @Override
    public MdCriterionList getCriteria() {
        return mGetter.getCriteria();
    }

    @Override
    public MdCommentList getComments() {
        return mGetter.getComments();
    }

    @Override
    public MdFactList getFacts() {
        return mGetter.getFacts();
    }

    @Override
    public MdImageList getImages() {
        return mGetter.getImages();
    }

    @Override
    public MdImageList getCovers() {
        return getImages().getCovers();
    }

    @Override
    public MdLocationList getLocations() {
        return mGetter.getLocations();
    }

    //Review methods
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
}
