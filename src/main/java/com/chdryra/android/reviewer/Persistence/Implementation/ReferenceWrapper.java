/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewDynamic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceObservers;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReferenceWrapper extends ReviewDynamic implements ReviewNode, ReviewReference{
    private ReviewReference mReference;

    public ReferenceWrapper(ReviewReference reference) {
        mReference = reference;
    }

    @Override
    public DataSubject getSubject() {
        return mReference.getInfo().getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReference.getInfo().getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mReference.getInfo().getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReference.getInfo().getPublishDate();
    }

    @Override
    public DataImage getCover() {
        return new DatumImage(getReviewId());
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public ReviewId getReviewId() {
        return mReference.getInfo().getReviewId();
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {

    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {

    }

    @Override
    public Review getReview() {
        return this;
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return null;
    }

    @Override
    public ReviewNode getRoot() {
        return this;
    }

    @Override
    public boolean isExpandable() {
        return false;
    }

    @Override
    public ReviewNode expand() {
        return this;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return new IdableDataList<>(getReviewId());
    }

    @Nullable
    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return null;
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return false;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return false;
    }

    @Override
    public ReviewInfo getInfo() {
        return mReference.getInfo();
    }

    @Override
    public void registerObserver(ReferenceObservers.SubjectObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.RatingObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.AuthorObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.DateObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.CoverObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.CriteriaObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.CommentsObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.FactsObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.ImagesObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.LocationsObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void registerObserver(ReferenceObservers.TagsObserver observer) {
        mReference.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.SubjectObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.RatingObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.AuthorObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.DateObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.CoverObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.CriteriaObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.CommentsObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.FactsObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.ImagesObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.LocationsObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void unregisterObserver(ReferenceObservers.TagsObserver observer) {
        mReference.unregisterObserver(observer);
    }

    @Override
    public void dereference(DereferenceCallback callback) {
        mReference.dereference(callback);
    }

    @Override
    public boolean isValid() {
        return mReference.isValid();
    }
}
