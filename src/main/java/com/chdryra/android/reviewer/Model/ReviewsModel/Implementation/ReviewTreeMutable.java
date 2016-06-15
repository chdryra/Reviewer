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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
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
public class ReviewTreeMutable extends ReviewDynamic implements ReviewNodeMutable, ReviewNode.NodeObserver, Review.ReviewObserver {
    private final Review mReview;
    private final MdDataList<ReviewNodeMutable> mChildren;
    private final ArrayList<NodeObserver> mObservers;
    private ReviewNodeMutable mParent;
    private boolean mRatingIsAverage = false;

    public ReviewTreeMutable(Review review, boolean ratingIsAverage) {
        mReview = review;
        mReview.registerObserver(this);
        mChildren = new MdDataList<>(getReviewId());
        mParent = null;
        mRatingIsAverage = ratingIsAverage;
        mObservers = new ArrayList<>();
        ReviewNode treeRepresentation = mReview.getTreeRepresentation();
        if(treeRepresentation != null) treeRepresentation.registerNodeObserver(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReview.getReviewId();
    }

    @Override
    public boolean addChild(ReviewNodeMutable childNode) {
        if (mChildren.containsId(childNode.getReviewId())) {
            return false;
        }
        mChildren.add(childNode);
        childNode.setParent(this);

        notifyNodeChanged();
        return true;
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!mChildren.containsId(reviewId)) return;
        ReviewNodeMutable childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);
        notifyNodeChanged();
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public void onNodeChanged() {
        notifyNodeChanged();
    }

    @Override
    public void onReviewChanged() {
        notifyNodeChanged();
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
    public void setParent(ReviewNodeMutable parentNode) {
        if (mParent != null && parentNode != null
                && mParent.getReviewId().equals(parentNode.getReviewId())) {
            return;
        }

        if (mParent != null) mParent.removeChild(getReviewId());
        mParent = parentNode;
        if (mParent != null) mParent.addChild(this);
        notifyNodeChanged();
    }

    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public boolean isExpandable() {
        return expand().getReview() != getReview();
    }

    @Override
    public ReviewNode expand() {
        return getReview().getTreeRepresentation();
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
    public IdableList<? extends DataImage> getCovers() {
        IdableList<DataImage> covers = new MdDataList<>(getReviewId());
        DataImage cover = getCover();
        if(cover.getBitmap() != null) covers.add(cover);
        for(ReviewNode child : getChildren()) {
            DataImage childCover = child.getCover();
            if(childCover.getBitmap() != null) covers.add(childCover);
        }

        return covers;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mRatingIsAverage;
    }

    //-------------Review methods--------------
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
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return getReviewData(new DataGetter<DataCriterion>() {
            @Override
            IdableList<? extends DataCriterion> getData(Review review) {
                return review.getCriteria();
            }
        });
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return getReviewData(new DataGetter<DataComment>() {
            @Override
            IdableList<? extends DataComment> getData(Review review) {
                return review.getComments();
            }
        });
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return getReviewData(new DataGetter<DataFact>() {
            @Override
            IdableList<? extends DataFact> getData(Review review) {
                return review.getFacts();
            }
        });
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return getReviewData(new DataGetter<DataImage>() {
            @Override
            IdableList<? extends DataImage> getData(Review review) {
                return review.getImages();
            }
        });
    }

    @Override
    public DataImage getCover() {
        return mReview.getCover();
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return getReviewData(new DataGetter<DataLocation>() {
            @Override
            IdableList<? extends DataLocation> getData(Review review) {
                return review.getLocations();
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTreeMutable)) return false;

        ReviewTreeMutable that = (ReviewTreeMutable) o;

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

    private void notifyNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
        notifyReviewObservers();
    }

    private <T extends HasReviewId> IdableList<T> getReviewData(DataGetter<T> getter) {
        MdDataList<T> data = new MdDataList<>(getReviewId());
        data.addAll(getter.getData(mReview));
        for (ReviewNode child : getChildren()) {
            data.addAll(getter.getData(child));
        }

        return data;
    }

    private abstract class DataGetter<T extends HasReviewId> {
        abstract IdableList<? extends T> getData(Review review);
    }
}
