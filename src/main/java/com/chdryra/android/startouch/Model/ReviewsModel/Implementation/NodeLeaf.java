/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

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
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

public class NodeLeaf extends ReviewNodeComponentBasic
        implements DataReference.InvalidationListener, ReviewReference.ReviewReferenceObserver {
    private final ReviewReference mReview;
    private final FactoryReferences mReferenceFactory;

    public NodeLeaf(ReviewReference review, FactoryReferences referenceFactory) {
        mReview = review;
        mReferenceFactory = referenceFactory;
        mReview.registerObserver(this);
    }

    @Override
    public void onSubjectChanged(DataSubject newSubject) {
        notifyOnNodeChanged();
    }

    @Override
    public void onRatingChanged(DataRating newRating) {
        notifyOnNodeChanged();
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        ReviewNodeComponent parent = getParentAsComponent();
        if (parent != null) parent.removeChild(getReviewId());
    }

    @Override
    public DataListRef<ReviewReference> getReviews() {
        return newRefDataList(mReview);
    }

    @Override
    public DataListRef<DataSubject> getSubjects() {
        return newRefDataList(mReview.getSubject());
    }

    @Override
    public DataListRef<DataAuthorId> getAuthorIds() {
        return newRefDataList(mReview.getAuthorId());
    }

    @Override
    public DataListRef<DataDate> getDates() {
        return newRefDataList(mReview.getPublishDate());
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mReview.getCover();
    }

    @Override
    public DataListRef<DataCriterion> getCriteria() {
        return mReview.getCriteria();
    }

    @Override
    public CommentListRef getComments() {
        return mReview.getComments();
    }

    @Override
    public DataListRef<DataFact> getFacts() {
        return mReview.getFacts();
    }

    @Override
    public DataListRef<DataImage> getImages() {
        return mReview.getImages();
    }

    @Override
    public DataListRef<DataLocation> getLocations() {
        return mReview.getLocations();
    }

    @Override
    public DataListRef<DataTag> getTags() {
        return mReview.getTags();
    }

    @Override
    public void addChild(ReviewNodeComponent childNode) {

    }

    @Override
    public void removeChild(ReviewId reviewId) {

    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return null;
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return false;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return new IdableDataList<>(getReviewId());
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(this);
    }

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
        return mReview.getRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mReview.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public boolean isLeaf() {
        return mReview.isValidReference();
    }

    @Nullable
    @Override
    public ReviewReference getReference() {
        return mReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeLeaf)) return false;
        if (!super.equals(o)) return false;

        NodeLeaf nodeLeaf = (NodeLeaf) o;

        return mReview.equals(nodeLeaf.mReview);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mReview.hashCode();
        return result;
    }

    private <T extends HasReviewId> DataListRef<T> newRefDataList(T datum) {
        IdableList<T> references = new IdableDataList<>(getReviewId());
        references.add(datum);
        return mReferenceFactory.newWrapper(references);
    }
}
