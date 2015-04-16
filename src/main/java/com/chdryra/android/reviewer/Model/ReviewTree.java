/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer.Model;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * A non-editable and non-expandable {@link ReviewNode} wrapper for another node that guarantees no
 * more editing or expanding of the node. Has the same {@link ReviewId} as the wrapped node.
 * <p/>
 * <p>
 * Although a ReviewTree is unchangeable it may still be wrapped by another
 * {@link ReviewTreeNode},
 * thus acting as a fixed, published component of a new review tree with its own {@link ReviewId}.
 * </p>
 */
public class ReviewTree implements ReviewNode {
    private final ReviewNode mNode;

    public ReviewTree(ReviewNode node) {
        mNode = node;
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
    public ReviewIdableList<ReviewNode> getChildren() {
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
    public ReviewId getId() {
        return mNode.getId();
    }

    @Override
    public MdSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public MdRating getRating() {
        return mNode.getRating();
    }

    @Override
    public Author getAuthor() {
        return mNode.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mNode.getPublishDate();
    }

    @Override
    public MdCommentList getComments() {
        return mNode.getComments();
    }

    @Override
    public boolean hasComments() {
        return mNode.hasComments();
    }

    @Override
    public MdFactList getFacts() {
        return mNode.getFacts();
    }

    @Override
    public boolean hasFacts() {
        return mNode.hasFacts();
    }

    @Override
    public MdImageList getImages() {
        return mNode.getImages();
    }

    @Override
    public boolean hasImages() {
        return mNode.hasImages();
    }

    @Override
    public MdLocationList getLocations() {
        return mNode.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mNode.hasLocations();
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
