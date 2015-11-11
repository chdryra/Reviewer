/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthorReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.Interfaces.Data.DataDateReview;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataRating;
import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.TreeMethods.ReviewTreeComparer;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewNode;

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node that guarantees no
 * more editing or expanding of the node. Has the same {@link MdReviewId} as the wrapped node.
 * <p/>
 * <p>
 * Although a ReviewTree is unchangeable it may still be wrapped by another
 * {@link ReviewTreeNode},
 * thus acting as a fixed, published component of a new review tree with its own {@link MdReviewId}.
 * </p>
 */
public class ReviewTree implements ReviewNode {
    private final ReviewNode mNode;

    //Constructors
    public ReviewTree(ReviewNode node) {
        mNode = node;
    }

    //Overridden

    @Override
    public String getReviewId() {
        return mNode.getReviewId();
    }

    @Override
    public Review getReview() {
        return mNode.getReview();
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
    public ReviewNode expand() {
        return mNode.expand();
    }

    @Override
    public ReviewNode getChild(String reviewId) {
        return mNode.getChild(reviewId);
    }

    @Override
    public boolean hasChild(String reviewId) {
        return hasChild(reviewId);
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
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mNode.isRatingAverageOfChildren();
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return mNode.getCriteria();
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return mNode.getComments();
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return mNode.getFacts();
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return mNode.getImages();
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return mNode.getCovers();
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return mNode.getLocations();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNode)) return false;

        ReviewNode that = (ReviewNode) o;
        return ReviewTreeComparer.compareTrees(this, that);
    }

    @Override
    public int hashCode() {
        return mNode.hashCode();
    }
}
