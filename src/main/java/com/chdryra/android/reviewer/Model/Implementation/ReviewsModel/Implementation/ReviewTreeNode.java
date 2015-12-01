/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 February, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataRating;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewNode;

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
    private static final ReviewTreeComparer COMPARER = new ReviewTreeComparer();

    private final MdReviewId mId;
    private final Review mReview;
    private final MdDataList<ReviewNode> mChildren;
    private ReviewNodeComponent mParent;
    private boolean mRatingIsAverage = false;
    private FactoryReviewNode mNodeFactory;

    //Constructors
    public ReviewTreeNode(MdReviewId nodeId, Review review,
                          boolean ratingIsAverage,
                          FactoryReviewNode nodeFactory) {
        mId = nodeId;
        mReview = review;
        mChildren = new MdDataList<>(nodeId);
        mParent = null;
        mRatingIsAverage = ratingIsAverage;
        mNodeFactory = nodeFactory;
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
        removeChild((ReviewTreeNode) mChildren.getItem(reviewId));
    }

    @Override
    public ReviewNode makeTree() {
        return mNodeFactory.createReviewNode(this);
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
    public boolean isExpandable() {
        return expand().getReview() != getReview();
    }

    @Override
    public ReviewNode expand() {
        return getReview().getTreeRepresentation();
    }

    @Override
    public ReviewNode getChild(String reviewId) {
        return mChildren.getItem(reviewId);
    }

    @Override
    public boolean hasChild(String reviewId) {
        return mChildren.containsId(reviewId);
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
        return mRatingIsAverage ? getAverageRating() : mReview.getRating();
    }

    @NonNull
    private DataRating getAverageRating() {
        float rating = 0f;
        int weight = 0;
        for(ReviewNode child : getChildren()) {
            DataRating childRating = child.getRating();
            rating += childRating.getRating() * childRating.getRatingWeight();
            weight += childRating.getRatingWeight();
        }
        if(weight > 0) rating /= weight;
        return new MdRating(mId, rating, weight);
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
    public IdableList<? extends DataCriterionReview> getCriteria() {
        return getReviewData(new DataGetter<DataCriterionReview>() {
            @Override
            IdableList<? extends DataCriterionReview> getData(Review review) {
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
    public IdableList<? extends DataImage> getCovers() {
        return getReviewData(new DataGetter<DataImage>() {
            @Override
            IdableList<? extends DataImage> getData(Review review) {
                return review.getCovers();
            }
        });
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

    private <T extends DataReviewIdable> IdableList<T> getReviewData(DataGetter<T> getter) {
        MdDataList<T> data = new MdDataList<>(mId);
        data.addAll(getter.getData(mReview));
        for(ReviewNode child : getChildren()) {
            data.addAll(getter.getData(child));
        }

        return data;
    }

    //Review methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNode)) return false;

        ReviewNode that = (ReviewNode) o;
        return COMPARER.compareNodes(this, that);
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

    private abstract class DataGetter<T extends DataReviewIdable> {
        abstract IdableList<? extends T> getData(Review review);
    }
}
