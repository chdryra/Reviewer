/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

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
 * {@link ReviewTreeExpandable},
 * thus acting as a fixed, published component of a new review tree with its own {@link ReviewId}.
 * </p>
 */
class ReviewTree implements ReviewNode {
    private ReviewNode mRoot;

    ReviewTree(ReviewNode root) {
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
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        visitorReviewNode.visit(mRoot);
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
    public ReviewNode getReviewNode() {
        return this;
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
    public MdUrlList getUrls() {
        return mRoot.getUrls();
    }

    @Override
    public boolean hasUrls() {
        return mRoot.hasUrls();
    }

    @Override
    public MdLocationList getLocations() {
        return mRoot.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mRoot.hasLocations();
    }
}
