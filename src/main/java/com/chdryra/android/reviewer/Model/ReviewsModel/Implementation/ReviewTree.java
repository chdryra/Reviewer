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

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import org.jetbrains.annotations.NotNull;

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node.
 */
public class ReviewTree extends ReviewNodeBasic implements ReviewNode, ReviewNode.NodeObserver {
    private ReviewNode mNode;

    public ReviewTree(@NotNull ReviewNode node) {
        setNode(node);
    }

    protected void setNode(ReviewNode node) {
        if (mNode != null) mNode.unregisterObserver(this);

        mNode = node;
        mNode.registerObserver(this);
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
    public DataAuthorId getAuthorId() {
        return mNode.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public RefDataList<ReviewReference> getReviews() {
        return mNode.getReviews();
    }

    @Override
    public RefDataList<DataSubject> getSubjects() {
        return mNode.getSubjects();
    }

    @Override
    public RefDataList<DataAuthorId> getAuthorIds() {
        return mNode.getAuthorIds();
    }

    @Override
    public RefDataList<DataDate> getDates() {
        return mNode.getDates();
    }

    @Override
    public void onNodeChanged() {
        notifyOnNodeChanged();
    }

    @Override
    public void onDescendantsChanged() {
        notifyOnDescendantsChanged();
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mNode.getCover();
    }

    @Override
    public RefDataList<DataCriterion> getCriteria() {
        return mNode.getCriteria();
    }

    @Override
    public RefCommentList getComments() {
        return mNode.getComments();
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public RefDataList<DataTag> getTags() {
        return mNode.getTags();
    }
}
