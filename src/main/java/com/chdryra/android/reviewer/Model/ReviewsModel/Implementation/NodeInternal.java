/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

/**
 * Creates a new unique {@link MdReviewId} if required so can represent a new review structure even
 * though it wraps an existing review.
 * </p>
 * <p/>
 * <p>
 * Wraps a {@link Review} object in a node structure with potential children and a parent.
 * </p>
 */
public class NodeInternal extends ReviewNodeBasic implements ReviewNodeComponent,
ReferenceBinder.DataBinder, ReferenceBinder.DataSizeBinder{
    private final ReviewReference mReview;
    private final MdDataList<ReviewNodeComponent> mChildren;
    private ReviewNodeComponent mParent;
    private ReferenceBinder mBinder;
    private ArrayList<ReferenceBinder> mChildBinders;

    private boolean mRatingIsAverage = false;

    public NodeInternal(ReviewReference review, boolean ratingIsAverage) {
        mReview = review;
        mParent = null;
        mRatingIsAverage = ratingIsAverage;

        mChildren = new MdDataList<>(getReviewId());
        mChildBinders = new ArrayList<>();

        mBinder = newBinder(review);
    }

    private ReferenceBinder newBinder(ReviewReference reference) {
        ReferenceBinder binder = new ReferenceBinder(reference);
        binder.registerDataBinder(this);
        binder.registerSizeBinder(this);

        return binder;
    }

    @Override
    public boolean addChild(ReviewNodeComponent child) {
        if (mChildren.containsId(child.getReviewId())) {
            return false;
        }
        mChildren.add(child);
        child.setParent(this);


        notifyNodeObservers();
        return true;
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!mChildren.containsId(reviewId)) return;
        ReviewNodeComponent childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);
        notifyNodeObservers();
    }

    @Override
    public ReviewReference getReference() {
        return mReview;
    }

    @Override
    public ReviewNode getParent() {
        return mParent;
    }

    @Override
    public void setParent(ReviewNodeComponent parentNode) {
        if (mParent != null && parentNode != null
                && mParent.getReviewId().equals(parentNode.getReviewId())) {
            return;
        }

        if (mParent != null) mParent.removeChild(getReviewId());
        mParent = parentNode;
        if (mParent != null) mParent.addChild(this);
        notifyNodeObservers();
    }

    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public boolean isExpandable() {
        return expand().getReference() != getReference();
    }

    @Override
    public ReviewNode expand() {
        return getReference().asNode();
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return mChildren.getItem(reviewId);
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return mChildren.containsId(reviewId);
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        MdDataList<ReviewNode> children = new MdDataList<>(mChildren.getReviewId());
        children.addAll(mChildren);
        return children;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mRatingIsAverage;
    }

    //-------------Review Reference methods--------------
    @Override
    public ReviewId getReviewId() {
        return mReview.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mRatingIsAverage ? getAverageRating() : mReview.getRating();
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
    public ReviewNode asNode() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInternal)) return false;

        NodeInternal that = (NodeInternal) o;

        if (mRatingIsAverage != that.mRatingIsAverage) return false;
        if (!mReview.equals(that.mReview)) return false;
        if (!mChildren.equals(that.mChildren)) return false;
        return !(mParent != null ? !mParent.equals(that.mParent) : that.mParent != null);

    }

    @Override
    public int hashCode() {
        int result = mReview.hashCode();
        result = 31 * result + mChildren.hashCode();
        result = 31 * result + (mParent != null ? mParent.hashCode() : 0);
        result = 31 * result + (mRatingIsAverage ? 1 : 0);
        return result;
    }

    @NonNull
    private DataRating getAverageRating() {
        float rating = 0f;
        int weight = 0;
        for (ReviewNode child : getChildren()) {
            DataRating childRating = child.getRating();
            rating += childRating.getRating() * childRating.getRatingWeight();
            weight += childRating.getRatingWeight();
        }
        if (weight > 0) rating /= weight;
        return new MdRating(new MdReviewId(getReviewId()), rating, weight);
    }
}
