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
 * Primarily used as a publishing wrapper to add an author and date stamp to an editable
 * review and to protect it from further change.
 * </p>
 * <p/>
 * <p>
 * Although a ReviewTree is unchangeable it may still be wrapped by another
 * {@link ReviewNodeExpandable},
 * thus acting as a fixed, published component of a new review tree with its own {@link ReviewId}.
 * </p>
 */
class ReviewTree implements ReviewNode {
    private ReviewNode mNode;
    private Author     mAuthor;
    private Date       mPublishDate;

    ReviewTree(Author author, Date publishDate, ReviewNode node) {
        mNode = node;
        mAuthor = author;
        mPublishDate = publishDate;
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
    public RCollectionReview<ReviewNode> getChildren() {
        return mNode.getChildren();
    }

    @Override
    public boolean isRatingIsAverageOfChildren() {
        return mNode.isRatingIsAverageOfChildren();
    }

    @Override
    public void setRatingIsAverageOfChildren(boolean ratingIsAverage) {
        mNode.setRatingIsAverageOfChildren(ratingIsAverage);
    }

    @Override
    public RCollectionReview<ReviewNode> flattenTree() {
        return mNode.flattenTree();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        mNode.acceptVisitor(visitorReviewNode);
    }

    @Override
    public ReviewId getId() {
        return mNode.getId();
    }

    @Override
    public RDSubject getSubject() {
        return mNode.getSubject();
    }

    @Override
    public RDRating getRating() {
        return mNode.getRating();
    }

    @Override
    public ReviewNode getReviewNode() {
        return this;
    }

    @Override
    public Review publish(PublisherReviewTree publisher) {
        if (!isPublished()) {
            mNode = publisher.publish(mNode);
            mAuthor = mNode.getAuthor();
            mPublishDate = mNode.getPublishDate();
        }

        return this;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public Date getPublishDate() {
        return mPublishDate;
    }

    @Override
    public boolean isPublished() {
        return mAuthor != null && mPublishDate != null;
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
    public MdUrlList getUrls() {
        return mNode.getUrls();
    }

    @Override
    public boolean hasUrls() {
        return mNode.hasUrls();
    }

    @Override
    public MdLocationList getLocations() {
        return mNode.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mNode.hasLocations();
    }
}
