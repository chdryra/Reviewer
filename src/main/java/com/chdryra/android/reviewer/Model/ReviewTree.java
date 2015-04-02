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
    private final ReviewNode mRoot;

    public ReviewTree(ReviewNode root) {
        mRoot = root;
    }

    @Override
    public Review getReview() {
        return mRoot.getReview();
    }

    @Override
    public ReviewNode getParent() {
        return mRoot.getParent();
    }

    @Override
    public RCollectionReview<ReviewNode> getChildren() {
        return mRoot.getChildren();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {
        visitor.visit(mRoot);
    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return mRoot.isRatingAverageOfChildren();
    }

    @Override
    public ReviewId getId() {
        return mRoot.getId();
    }

    @Override
    public MdSubject getSubject() {
        return mRoot.getSubject();
    }

    @Override
    public MdRating getRating() {
        return mRoot.getRating();
    }

    @Override
    public Author getAuthor() {
        return mRoot.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mRoot.getPublishDate();
    }

    @Override
    public MdCommentList getComments() {
        return mRoot.getComments();
    }

    @Override
    public boolean hasComments() {
        return mRoot.hasComments();
    }

    @Override
    public MdFactList getFacts() {
        return mRoot.getFacts();
    }

    @Override
    public boolean hasFacts() {
        return mRoot.hasFacts();
    }

    @Override
    public MdImageList getImages() {
        return mRoot.getImages();
    }

    @Override
    public boolean hasImages() {
        return mRoot.hasImages();
    }

    @Override
    public MdLocationList getLocations() {
        return mRoot.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mRoot.hasLocations();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj.getClass() == getClass() || obj.getClass() == mRoot.getClass())) {
            return false;
        }

        ReviewNode objNode = (ReviewNode) obj;
        return getId().equals(objNode.getId());
    }
}
