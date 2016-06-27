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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.BindersManager;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewNodeComponentBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeSourceCallback extends ReviewNodeComponentBasic
        implements ReviewsSource.ReviewsSourceCallback {
    private ReviewNode mInitial;
    private ReviewNodeComponent mNode;

    public ReviewTreeSourceCallback(ReviewNode initial, BindersManager bindersManager) {
        super(bindersManager);
        mInitial = initial;
    }

    private void setNode(ReviewNodeComponent node) {
        mNode = node;
        mInitial = null;
        notifyNodeObservers();

    }

    @Override
    public void onMetaReviewCallback(RepositoryResult result) {
        ReviewNodeComponent node = result.getReviewNode();
        if (!result.isError() && node != null) {
            setNode(node);
        }
    }

    @Override
    public boolean addChild(ReviewNodeComponent childNode) {
        return false;
    }

    @Override
    public void addChildren(Iterable<ReviewNodeComponent> children) {

    }

    @Override
    public void removeChild(ReviewId reviewId) {

    }

    @Override
    public IdableList<ReviewNode> getChildren() {
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
    public ReviewId getReviewId() {
        return null;
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
    public ReviewNode asNode() {
        return null;
    }

    @Override
    public void getData(CoversCallback callback) {

    }

    @Override
    public void getData(TagsCallback callback) {

    }

    @Override
    public void getData(CriteriaCallback callback) {

    }

    @Override
    public void getData(ImagesCallback callback) {

    }

    @Override
    public void getData(CommentsCallback callback) {

    }

    @Override
    public void getData(LocationsCallback callback) {

    }

    @Override
    public void getData(FactsCallback callback) {

    }

    @Override
    public void getSize(TagsSizeCallback callback) {

    }

    @Override
    public void getSize(CriteriaSizeCallback callback) {

    }

    @Override
    public void getSize(ImagesSizeCallback callback) {

    }

    @Override
    public void getSize(CommentsSizeCallback callback) {

    }

    @Override
    public void getSize(LocationsSizeCallback callback) {

    }

    @Override
    public void getSize(FactsSizeCallback callback) {

    }

    @Override
    public void dereference(DereferenceCallback callback) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
