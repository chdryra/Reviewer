/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a new unique {@link MdReviewId} if required so can represent a new review structure even
 * though it wraps an existing review.
 * </p>
 * <p/>
 * <p>
 * Wraps a {@link Review} object in a node structure with potential children and a parent.
 * </p>
 */
public class NodeInternal extends ReviewNodeBasic implements ReviewNodeComponent,
        ReferenceBinder.DataBinder, ReferenceBinder.DataSizeBinder {
    private final ReviewInfo mMeta;
    private FactoryDataCollector mCollectorFactory;
    private final MdDataList<ReviewNodeComponent> mChildren;
    private Map<ReviewId, ReferenceBinder> mChildBinders;

    public NodeInternal(ReviewInfo meta, FactoryDataCollector collectorFactory) {
        mMeta = meta;
        mCollectorFactory = collectorFactory;
        mChildren = new MdDataList<>(getReviewId());
        mChildBinders = new HashMap<>();
    }

    @Override
    public boolean addChild(ReviewNodeComponent child) {
        if (mChildren.containsId(child.getReviewId())) return false;

        mChildren.add(child);
        child.setParent(this);

        bindToChild(child);

        notifyNodeObservers();

        return true;
    }

    private void bindToChild(ReviewNodeComponent child) {
        mChildBinders.put(child.getReviewId(), newBinder(child));
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        if (!mChildren.containsId(reviewId)) return;

        ReviewNodeComponent childNode = mChildren.getItem(reviewId);
        mChildren.remove(reviewId);
        if (childNode != null) childNode.setParent(null);

        unbindFromChild(reviewId);

        notifyNodeObservers();
    }

    private void unbindFromChild(ReviewId childId) {
        ReferenceBinder remove = mChildBinders.remove(childId);
        remove.unregisterDataBinder(this);
        remove.unregisterDataBinder(this);
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
    public DataAuthorReview getAuthor() {
        return mMeta.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mMeta.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return this;
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

    private ReferenceBinder newBinder(ReviewReference reference) {
        ReferenceBinder binder = new ReferenceBinder(reference);
        binder.registerDataBinder(this);
        binder.registerSizeBinder(this);

        return binder;
    }


    @Override
    public void onComments(IdableList<DataComment> comments, CallbackMessage message) {

    }

    @Override
    public void onNumComments(ReviewId id, int num, CallbackMessage message) {

    }

    @Override
    public void onCovers(IdableList<DataImage> covers, CallbackMessage message) {

    }

    @Override
    public void onCriteria(IdableList<DataCriterion> criteria, CallbackMessage message) {

    }

    @Override
    public void onNumCriteria(ReviewId id, int num, CallbackMessage message) {

    }

    @Override
    public void onFacts(IdableList<DataFact> facts, CallbackMessage message) {

    }

    @Override
    public void onNumFacts(ReviewId id, int num, CallbackMessage message) {

    }

    @Override
    public void onImages(IdableList<DataImage> images, CallbackMessage message) {

    }

    @Override
    public void onNumImages(ReviewId id, int num, CallbackMessage message) {

    }

    @Override
    public void onLocations(IdableList<DataLocation> locations, CallbackMessage message) {

    }

    @Override
    public void onNumLocations(ReviewId id, int num, CallbackMessage message) {

    }

    @Override
    public void getCovers(final CoversCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getTags(TagsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getCriteria(CriteriaCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getImages(ImagesCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getComments(CommentsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getLocations(LocationsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getFacts(FactsCallback callback) {
        mCollectorFactory.newCollector(getChildren(), callback).collect();
    }

    @Override
    public void getNumTags(TagsSizeCallback callback) {

    }

    @Override
    public void getNumCriteria(CriteriaSizeCallback callback) {

    }

    @Override
    public void getNumImages(ImagesSizeCallback callback) {

    }

    @Override
    public void getNumComments(CommentsSizeCallback callback) {

    }

    @Override
    public void getNumLocations(LocationsSizeCallback callback) {

    }

    @Override
    public void getNumFacts(FactsSizeCallback callback) {

    }

    @Override
    public void bind(ReferenceBinders.CoversBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {

    }

    @Override
    public void bindToTags(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void bindToCriteria(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void bindToImages(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void bindToComments(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void bindToLocations(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void bindToFacts(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.CoversBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {

    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {

    }

    @Override
    public void dereference(DereferenceCallback callback) {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void onTags(IdableList<DataTag> tags, CallbackMessage message) {

    }

    @Override
    public void onNumTags(ReviewId id, int num, CallbackMessage message) {

    }

    private class BindersManager {
        private List<ReferenceBinders.CoversBinder> mCovers;
        private List<ReferenceBinders.TagsBinder> mTags;
        private List<ReferenceBinders.CriteriaBinder> mCriteria;
        private List<ReferenceBinders.ImagesBinder> mImages;
        private List<ReferenceBinders.CommentsBinder> mComments;
        private List<ReferenceBinders.LocationsBinder> mLocations;
        private List<ReferenceBinders.FactsBinder> mFacts;

        private List<ReferenceBinders.SizeBinder> mNumCovers;
        private List<ReferenceBinders.SizeBinder> mNumTags;
        private List<ReferenceBinders.SizeBinder> mNumCriteria;
        private List<ReferenceBinders.SizeBinder> mNumImages;
        private List<ReferenceBinders.SizeBinder> mNumComments;
        private List<ReferenceBinders.SizeBinder> mNumLocations;
        private List<ReferenceBinders.SizeBinder> mNumFacts;
    }
}
