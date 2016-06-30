/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

public class NodeLeaf extends ReviewNodeComponentBasic implements ReviewNodeComponent {
    private final ReviewReference mReview;

    public NodeLeaf(ReviewReference review, BindersManagerMeta bindersManager,
                    FactoryVisitorReviewNode visitorFactory,
                    FactoryNodeTraverser traverserFactory) {
        super(bindersManager, visitorFactory, traverserFactory);
        mReview = review;
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
    public void getData(CoversCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getData(TagsCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getData(CriteriaCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getData(ImagesCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getData(CommentsCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getData(LocationsCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getData(FactsCallback callback) {
        mReview.getData(callback);
    }

    @Override
    public void getSize(TagsSizeCallback callback) {
        mReview.getSize(callback);
    }

    @Override
    public void getSize(CriteriaSizeCallback callback) {
        mReview.getSize(callback);
    }

    @Override
    public void getSize(ImagesSizeCallback callback) {
        mReview.getSize(callback);
    }

    @Override
    public void getSize(CommentsSizeCallback callback) {
        mReview.getSize(callback);
    }

    @Override
    public void getSize(LocationsSizeCallback callback) {
        mReview.getSize(callback);
    }

    @Override
    public void getSize(FactsSizeCallback callback) {
        mReview.getSize(callback);
    }

    @Override
    public void bind(ReferenceBinders.CoversBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {
        mReview.bind(binder);
    }

    @Override
    public void bindToTags(ReferenceBinders.SizeBinder binder) {
        mReview.bindToTags(binder);
    }

    @Override
    public void bindToCriteria(ReferenceBinders.SizeBinder binder) {
        mReview.bindToCriteria(binder);
    }

    @Override
    public void bindToImages(ReferenceBinders.SizeBinder binder) {
        mReview.bindToImages(binder);
    }

    @Override
    public void bindToComments(ReferenceBinders.SizeBinder binder) {
        mReview.bindToComments(binder);
    }

    @Override
    public void bindToLocations(ReferenceBinders.SizeBinder binder) {
        mReview.bindToLocations(binder);
    }

    @Override
    public void bindToFacts(ReferenceBinders.SizeBinder binder) {
        mReview.bindToFacts(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CoversBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {
        mReview.unbind(binder);
    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromTags(binder);
    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromCriteria(binder);
    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromImages(binder);
    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromComments(binder);
    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromLocations(binder);
    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        mReview.unbindFromFacts(binder);
    }

    @Override
    public void dereference(DereferenceCallback callback) {
        mReview.dereference(callback);
    }

    @Override
    public boolean isValidReference() {
        return mReview.isValidReference();
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
    public DataAuthorReview getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return this;
    }

    @Override
    public boolean isLeaf() {
        return isValidReference();
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
}
