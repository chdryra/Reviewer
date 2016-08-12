/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

public class NodeInternal extends ReviewNodeComponentBasic implements ReviewNode.NodeObserver{
    private DataReviewInfo mMeta;
    private FactoryMdReference mReferenceFactory;

    private ArrayList<NodeObserver> mObservers;
    private MdDataList<ReviewNodeComponent> mChildren;

    public NodeInternal(DataReviewInfo meta,
                        FactoryMdReference referenceFactory) {
        mMeta = meta;
        mReferenceFactory = referenceFactory;
        mChildren = new MdDataList<>(getReviewId());
        mObservers = new ArrayList<>();
    }

    @Override
    public ReviewListReference<ReviewReference> getReviews() {
        return mReferenceFactory.newReviewsList(this);
    }

    @Override
    public ReviewListReference<DataSubject> getSubjects() {
        return mReferenceFactory.newSubjectsList(this);
    }

    @Override
    public ReviewListReference<DataAuthorId> getAuthorIds() {
        return mReferenceFactory.newAuthorsList(this);
    }

    @Override
    public ReviewListReference<DataDate> getDates() {
        return mReferenceFactory.newDatesList(this);
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return null;
    }

    @Override
    public ReviewListReference<DataCriterion> getCriteria() {
        return mReferenceFactory.newCriteriaList(this);
    }

    @Override
    public ReviewListReference<DataComment> getComments() {
        return mReferenceFactory.newCommentsList(this);
    }

    @Override
    public ReviewListReference<DataFact> getFacts() {
        return mReferenceFactory.newFactsList(this);
    }

    @Override
    public ReviewListReference<DataImage> getImages() {
        return mReferenceFactory.newImagesList(this);
    }

    @Override
    public ReviewListReference<DataLocation> getLocations() {
        return mReferenceFactory.newLocationsList(this);
    }

    @Override
    public ReviewListReference<DataTag> getTags() {
        return mReferenceFactory.newTagsList(this);
    }

    @Override
    public void unregisterObserver(NodeObserver binder) {
        if (mObservers.contains(binder)) mObservers.remove(binder);
    }

    @Override
    public void registerObserver(NodeObserver binder) {
        if (!mObservers.contains(binder)) mObservers.add(binder);
    }

    @Override
    public void addChild(ReviewNodeComponent child) {
        if (mChildren.containsId(child.getReviewId())) return;

        mChildren.add(child);
        child.setParent(this);

        registerWithChild(child);

        notifyOnChildAdded(child);
    }

    @Override
    public void addChildren(Iterable<ReviewNodeComponent> children) {
        for(ReviewNodeComponent child : children) {
            if (mChildren.containsId(child.getReviewId())) continue;
            mChildren.add(child);
            child.setParent(this);
        }

        for(ReviewNodeComponent child : children) {
            registerWithChild(child);
        }

        for(ReviewNodeComponent child : children) {
            notifyOnChildAdded(child);
        }
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!hasChild(reviewId)) return;

        ReviewNodeComponent childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);

        unregisterWithChild(reviewId);

        if (childNode != null) notifyOnChildRemoved(childNode);
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
        return true;
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
        return new MdRating(new MdReviewId(getReviewId()), rating, weight);
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
        notifyOnNodeChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyOnNodeChanged();
    }

    @Override
    public void onNodeChanged() {
        notifyOnNodeChanged();
    }
}
