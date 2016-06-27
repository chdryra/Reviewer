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

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node that guarantees no
 * more editing or expanding of the node. Has the same {@link MdReviewId} as the wrapped node.
 * <p/>
 * <p>
 * Although a ReviewTree is unchangeable it may still be wrapped by another
 * {@link ReviewTree},
 * thus acting as a fixed, published component of a new review tree with its own {@link MdReviewId}.
 * </p>
 */
public class ReviewTree extends ReviewNodeBasic implements ReviewNode, ReviewNode.NodeObserver {
    private final ArrayList<NodeObserver> mObservers;
    private ReviewNode mNode;

    public ReviewTree(@NotNull ReviewNode node, BindersManager bindersManager) {
        super(bindersManager)
;        mNode = node;
        mObservers = new ArrayList<>();
        node.registerNodeObserver(this);
    }

    @Override
    public void onNodeChanged() {
        notifyNodeChanged();
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
        mObservers.remove(observer);
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
    public void dereference(DereferenceCallback callback) {
        mNode.dereference(callback);
    }

    @Override
    public boolean isValid() {
        return mNode.isValid();
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

    private void notifyNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
    }
}
