/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

public class NodeLeaf extends ReviewNodeComponentBasic
        implements DataReference.InvalidationListener {
    private final ReviewReference mReview;
    private final FactoryReference mReferenceFactory;

    public NodeLeaf(ReviewReference review, FactoryReference referenceFactory) {
        mReview = review;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        ReviewNodeComponent parent = getParentAsComponent();
        if (parent != null) parent.removeChild(getReviewId());
    }

    @Override
    public RefDataList<ReviewReference> getReviews() {
        return newEmptyReference();
    }

    @Override
    public RefDataList<DataSubject> getSubjects() {
        return newEmptyReference();
    }

    @Override
    public RefDataList<DataAuthorId> getAuthorIds() {
        return newEmptyReference();
    }

    @Override
    public RefDataList<DataDate> getDates() {
        return newEmptyReference();
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mReview.getCover();
    }

    @Override
    public RefDataList<DataCriterion> getCriteria() {
        return mReview.getCriteria();
    }

    @Override
    public RefCommentList getComments() {
        return mReview.getComments();
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return mReview.getFacts();
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return mReview.getImages();
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return mReview.getLocations();
    }

    @Override
    public RefDataList<DataTag> getTags() {
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

    @NonNull
    private <T extends HasReviewId> RefDataList<T> newEmptyReference() {
        return mReferenceFactory.newWrapper(new IdableDataList<T>(mReview.getReviewId()));
    }
}
