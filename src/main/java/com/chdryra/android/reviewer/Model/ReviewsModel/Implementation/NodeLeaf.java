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

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

public class NodeLeaf extends ReviewNodeComponentBasic implements ReviewNodeComponent,
        DataReference.InvalidationListener {
    private final ReviewReference mReview;

    public NodeLeaf(ReviewReference review, FactoryReviewNode nodeFactory) {
        super(nodeFactory);
        mReview = review;
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        ReviewNodeComponent parent = getParentAsComponent();
        if (parent != null) parent.removeChild(getReviewId());
    }

    @Override
    public ReviewListReference<ReviewReference> getReviews() {
        return newStaticReference(mReview);
    }

    @Override
    public ReviewListReference<DataSubject> getSubjects() {
        return newStaticReference(mReview.getSubject());
    }

    @Override
    public ReviewListReference<DataAuthorId> getAuthorIds() {
        return newStaticReference(mReview.getAuthorId());
    }

    @Override
    public ReviewListReference<DataDate> getDates() {
        return newStaticReference(mReview.getPublishDate());
    }

    @Override
    public ReviewItemReference<DataSize> getNumReviews() {
        return sizeOne();
    }

    @Override
    public ReviewItemReference<DataSize> getNumSubjects() {
        return sizeOne();
    }

    @Override
    public ReviewItemReference<DataSize> getNumAuthors() {
        return sizeOne();
    }

    @Override
    public ReviewItemReference<DataSize> getNumDates() {
        return sizeOne();
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mReview.getCover();
    }

    @Override
    public ReviewListReference<DataCriterion> getCriteria() {
        return mReview.getCriteria();
    }

    @Override
    public ReviewListReference<DataComment> getComments() {
        return mReview.getComments();
    }

    @Override
    public ReviewListReference<DataFact> getFacts() {
        return mReview.getFacts();
    }

    @Override
    public ReviewListReference<DataImage> getImages() {
        return mReview.getImages();
    }

    @Override
    public ReviewListReference<DataLocation> getLocations() {
        return mReview.getLocations();
    }

    @Override
    public ReviewListReference<DataTag> getTags() {
        return mReview.getTags();
    }

    @Override
    public void addChild(ReviewNodeComponent childNode) {

    }

    @Override
    public void addChildren(Iterable<ReviewNodeComponent> children) {

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
        return new MdDataList<>(getReviewId());
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
    private <T extends HasReviewId> ReviewListReference<T> newStaticReference(T item) {
        IdableList<T> data = new IdableDataList<>(mReview.getReviewId());
        data.add(item);
        return new StaticListReference<>(mReview.getReviewId(), data);
    }

    @NonNull
    private ReviewItemReference<DataSize> sizeOne() {
        DataSize size = new DatumSize(getReviewId(), 1);
        return new StaticItemReference<>(mReview.getReviewId(), size);
    }
}
