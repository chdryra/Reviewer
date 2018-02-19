/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

public class NodeDefault extends ReviewNodeComponentBasic implements ReviewNode.NodeObserver{
    private DataReview mMeta;
    private final FactoryMdReference mReferenceFactory;
    private final IdableList<ReviewNodeComponent> mChildren;

    public NodeDefault(DataReview meta, FactoryMdReference referenceFactory) {
        mMeta = meta;
        mReferenceFactory = referenceFactory;
        mChildren = new IdableDataList<>(getReviewId());
    }

    protected void setMeta(DataReview meta) {
        mMeta = meta;
        notifyOnNodeChanged();
    }

    @Override
    public DataListRef<ReviewReference> getReviews() {
        return mReferenceFactory.newReviewsList(this);
    }

    @Override
    public DataListRef<DataSubject> getSubjects() {
        return mReferenceFactory.newSubjectsList(this);
    }

    @Override
    public DataListRef<DataAuthorId> getAuthorIds() {
        return mReferenceFactory.newAuthorsList(this);
    }

    @Override
    public DataListRef<DataDate> getDates() {
        return mReferenceFactory.newDatesList(this);
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mReferenceFactory.newCoverReferenceForNode(this);
    }

    @Override
    public DataListRef<DataCriterion> getCriteria() {
        return mReferenceFactory.newCriteriaList(this);
    }

    @Override
    public CommentListRef getComments() {
        return mReferenceFactory.newCommentsList(this);
    }

    @Override
    public DataListRef<DataFact> getFacts() {
        return mReferenceFactory.newFactsList(this);
    }

    @Override
    public DataListRef<DataImage> getImages() {
        return mReferenceFactory.newImagesList(this);
    }

    @Override
    public DataListRef<DataLocation> getLocations() {
        return mReferenceFactory.newLocationsList(this);
    }

    @Override
    public DataListRef<DataTag> getTags() {
        return mReferenceFactory.newTagsList(this);
    }

    @Override
    public void addChild(ReviewNodeComponent child) {
        if (hasChild(child.getReviewId())) return;

        mChildren.add(child);
        child.setParent(this);

        registerWithChild(child);

        notifyOnChildAdded(child);
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        ReviewNodeComponent childNode = (ReviewNodeComponent) getChild(reviewId);
        if(childNode == null) return;

        mChildren.remove(childNode);
        childNode.setParent(null);

        unregisterWithChild(reviewId);

        notifyOnChildRemoved(childNode);
    }

    @Override
    @Nullable
    public ReviewNode getChild(ReviewId reviewId) {
        for(ReviewNode child : mChildren) {
            if(child.getReviewId().equals(reviewId)) return child;
        }

        return null;
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return getChild(reviewId) != null;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        IdableList<ReviewNode> children = new IdableDataList<>(mChildren.getReviewId());
        children.addAll(mChildren);
        return children;
    }

    @Override
    public boolean isLeaf() {
        return mChildren.size() == 0;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

    //-------------Review Reference methods--------------
    @Override
    public ReviewId getReviewId() {
        return mMeta.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mMeta.getSubject();
    }

    @Override
    public DataRating getRating() {
        return getAverageRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mMeta.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mMeta.getPublishDate();
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
        return new DatumRating(getReviewId(), rating, weight);
    }

    private void unregisterWithChild(ReviewId childId) {
        ReviewNode child = getChild(childId);
        if(child != null) child.unregisterObserver(this);
    }

    private void registerWithChild(ReviewNode child) {
        child.registerObserver(this);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        onTreeChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        onTreeChanged();
    }

    @Override
    public void onNodeChanged() {
        onTreeChanged();
    }

    @Override
    public void onTreeChanged() {
        notifyOnTreeChanged();
    }
}
