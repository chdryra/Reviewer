/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

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
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

import org.jetbrains.annotations.NotNull;

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node.
 */
public class ReviewTree extends ReviewNodeBasic implements ReviewNode.NodeObserver {
    private ReviewNode mNode;

    public ReviewTree(@NotNull ReviewNode node) {
        setNode(node);
    }

    protected void setNode(ReviewNode node) {
        if (mNode != null) mNode.unregisterObserver(this);

        mNode = node;
        mNode.registerObserver(this);
        notifyOnTreeChanged();
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
    public DataListRef<ReviewReference> getReviews() {
        return mNode.getReviews();
    }

    @Override
    public DataListRef<DataSubject> getSubjects() {
        return mNode.getSubjects();
    }

    @Override
    public DataListRef<DataAuthorId> getAuthorIds() {
        return mNode.getAuthorIds();
    }

    @Override
    public DataListRef<DataDate> getDates() {
        return mNode.getDates();
    }

    @Override
    public void onNodeChanged() {
        notifyOnNodeChanged();
    }

    @Override
    public void onTreeChanged() {
        notifyOnTreeChanged();
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return mNode.getCover();
    }

    @Override
    public DataListRef<DataCriterion> getCriteria() {
        return mNode.getCriteria();
    }

    @Override
    public CommentListRef getComments() {
        return mNode.getComments();
    }

    @Override
    public DataListRef<DataFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public DataListRef<DataImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public DataListRef<DataLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public DataListRef<DataTag> getTags() {
        return mNode.getTags();
    }
}
