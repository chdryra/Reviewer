/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import org.jetbrains.annotations.NotNull;

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node.
 */
public class ReviewTree extends ReviewNodeBasic implements
        ReviewNode,
        ReviewNode.NodeObserver,
        MetaBinder.MetaDataBinder,
        MetaBinder.MetaDataSizeBinder {
    private final FactoryBinders mBindersFactory;
    private ReferenceBinder mNodeBinder;
    private ReviewNode mNode;

    public ReviewTree(@NotNull ReviewNode node, FactoryBinders bindersFactory) {
        super(bindersFactory.newMetaBindersManager());
        mNode = node;
        mBindersFactory = bindersFactory;
        mNode.registerObserver(this);
    }

    protected void setNode(ReviewNode node) {
        if(mNode != null) {
            mNode.unregisterObserver(this);
            mNodeBinder.unregisterDataBinder(this);
            mNodeBinder.unregisterSizeBinder(this);
        }

        mNode = node;
        mNode.registerObserver(this);
        mNodeBinder = mBindersFactory.bindTo(mNode);
        mNodeBinder.registerDataBinder(this);
        mNodeBinder.registerSizeBinder(this);

        notifyOnNodeChanged();
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        notifyOnChildAdded(child);
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyOnChildRemoved(child);
    }

    @Override
    public ReviewId getReviewId() {
        return mNode.getReviewId();
    }

    @Override
    public ReviewNode getParent() {
        return mNode.getParent();
    }

    @Override
    public ReviewNode getRoot() {
        return mNode.getRoot();
    }

    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return mNode.getChild(reviewId);
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return mNode.hasChild(reviewId);
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return mNode.getChildren();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(mNode);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mNode.isRatingAverageOfChildren();
    }

    @Override
    public DataSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mNode.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mNode.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public ReviewNode asNode() {
        return this;
    }

    @Override
    public void getData(ReviewsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(SubjectsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(AuthorsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(DatesCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(CoversCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(TagsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(CriteriaCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(ImagesCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(CommentsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(LocationsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getData(FactsCallback callback) {
        mNode.getData(callback);
    }

    @Override
    public void getSize(ReviewsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(SubjectsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(AuthorsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(DatesSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(TagsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(CriteriaSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(ImagesSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(CommentsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(LocationsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void getSize(FactsSizeCallback callback) {
        mNode.getSize(callback);
    }

    @Override
    public void onAuthors(IdableList<? extends DataAuthorReview> Authors, CallbackMessage message) {
        notifyAuthorsBinders();
    }

    @Override
    public void onNumAuthors(DataSize size, CallbackMessage message) {
        notifyNumAuthorsBinders();
    }

    @Override
    public void onDates(IdableList<? extends DataDateReview> Dates, CallbackMessage message) {
        notifyDatesBinders();
    }

    @Override
    public void onNumDates(DataSize size, CallbackMessage message) {
        notifyNumDatesBinders();
    }

    @Override
    public void onReviews(IdableList<ReviewReference> reviews, CallbackMessage message) {
        notifyReviewsBinders();
    }

    @Override
    public void onNumReviews(DataSize size, CallbackMessage message) {
        notifyNumReviewsBinders();
    }

    @Override
    public void onSubjects(IdableList<? extends DataSubject> subjects, CallbackMessage message) {
        notifySubjectsBinders();
    }

    @Override
    public void onNumSubjects(DataSize size, CallbackMessage message) {
        notifyNumSubjectsBinders();
    }

    @Override
    public void onComments(IdableList<? extends DataComment> comments, CallbackMessage message) {
        notifyCommentsBinders();
    }

    @Override
    public void onNumComments(DataSize size, CallbackMessage message) {
        notifyNumCommentsBinders();
    }

    @Override
    public void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message) {
        notifyCoversBinders();
    }

    @Override
    public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage message) {
        notifyCriteriaBinders();
    }

    @Override
    public void onNumCriteria(DataSize size, CallbackMessage message) {
        notifyNumCriteriaBinders();
    }

    @Override
    public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
        notifyFactsBinders();
    }

    @Override
    public void onNumFacts(DataSize size, CallbackMessage message) {
        notifyNumFactsBinders();
    }

    @Override
    public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
        notifyImagesBinders();
    }

    @Override
    public void onNumImages(DataSize size, CallbackMessage message) {
        notifyNumImagesBinders();
    }

    @Override
    public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage message) {
        notifyLocationsBinders();
    }

    @Override
    public void onNumLocations(DataSize size, CallbackMessage message) {
        notifyNumLocationsBinders();
    }

    @Override
    public void onNodeChanged() {
        notifyOnNodeChanged();
    }

    @Override
    public void onTags(IdableList<? extends DataTag> tags, CallbackMessage message) {
        notifyTagsBinders();
    }

    @Override
    public void onNumTags(DataSize size, CallbackMessage message) {
        notifyNumTagsBinders();
    }

    @Override
    public void dereference(DereferenceCallback callback) {
        mNode.dereference(callback);
    }

    @Override
    public boolean isValidReference() {
        return mNode.isValidReference();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTree)) return false;

        ReviewTree that = (ReviewTree) o;

        return mNode.equals(that.mNode);

    }

    @Override
    public int hashCode() {
        return mNode.hashCode();
    }
}
