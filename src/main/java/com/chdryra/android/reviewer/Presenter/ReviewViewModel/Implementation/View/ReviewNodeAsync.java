/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSourceCallback;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeAsync implements ReviewNode, ReviewsSourceCallback, DataObservable {

    private ReviewNode mNode;
    private ArrayList<DataObservable.DataObserver> mObservers;

    public ReviewNodeAsync(ReviewNode initial) {
        mNode = initial;
        mObservers = new ArrayList<>();
    }

    @Override
    public ReviewId getReviewId() {
        return mNode.getReviewId();
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
    public ReviewNode getRoot() {
        return mNode.getRoot();
    }

    @Override
    public boolean isExpandable() {
        return mNode.isExpandable();
    }

    @Override
    public ReviewNode expand() {
        return mNode.expand();
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return mNode.getChild(reviewId);
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return mNode.hasChild(reviewId);
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return mNode.getChildren();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(mNode);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mNode.isRatingAverageOfChildren();
    }

    @Override
    public DataSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mNode.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mNode.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mNode.isRatingAverageOfChildren();
    }

    @Override
    public IdableList<? extends DataCriterionReview> getCriteria() {
        return mNode.getCriteria();
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return mNode.getComments();
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return mNode.getCovers();
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public void registerDataObserver(DataObservable.DataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterDataObserver(DataObservable.DataObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void notifyDataObservers() {
        for (DataObservable.DataObserver observer : mObservers) {
            observer.onDataChanged();
        }
    }

    @Override
    public void onMetaReview(@Nullable ReviewNode review, RepositoryError error) {
        if(review!= null && !error.isError()) {
            mNode = review;
            notifyDataObservers();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeAsync)) return false;

        ReviewNodeAsync that = (ReviewNodeAsync) o;

        if (mNode != null ? !mNode.equals(that.mNode) : that.mNode != null) return false;
        return !(mObservers != null ? !mObservers.equals(that.mObservers) : that.mObservers !=
                null);

    }

    @Override
    public int hashCode() {
        int result = mNode != null ? mNode.hashCode() : 0;
        result = 31 * result + (mObservers != null ? mObservers.hashCode() : 0);
        return result;
    }
}
