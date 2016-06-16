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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;
import com.chdryra.android.reviewer.Persistence.Implementation.ReferenceWrapper;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeReference implements ReviewNodeMutable {
    private ReferenceWrapper mReference;

    public ReviewNodeReference(ReferenceWrapper reference) {
        mReference = reference;
    }

    @Override
    public boolean addChild(ReviewNodeMutable childNode) {
        return false;
    }

    @Override
    public void removeChild(ReviewId reviewId) {

    }

    @Override
    public void setParent(@Nullable ReviewNodeMutable parent) {

    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {

    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {

    }

    @Override
    public Review getReview() {
        return null;
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return null;
    }

    @Override
    public ReviewNode getRoot() {
        return null;
    }

    @Override
    public boolean isExpandable() {
        return false;
    }

    @Override
    public ReviewNode expand() {
        return null;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return null;
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return null;
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

    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return false;
    }

    @Override
    public void registerObserver(ReviewObserver observer) {

    }

    @Override
    public void unregisterObserver(ReviewObserver observer) {

    }

    @Override
    public DataSubject getSubject() {
        return null;
    }

    @Override
    public DataRating getRating() {
        return null;
    }

    @Override
    public DataAuthorReview getAuthor() {
        return null;
    }

    @Override
    public DataDateReview getPublishDate() {
        return null;
    }

    @Override
    public DataImage getCover() {
        return null;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return null;
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return null;
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return null;
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return null;
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return null;
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return null;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public ReviewId getReviewId() {
        return null;
    }
}
