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
    private Review                        mReview;
    private RCollectionReview<ReviewNode> mChildren;
    private boolean mIsRatingAverage = false;
    private MdRating mAverageRating;

    ReviewTree(Review root) {
        mReview = root;
        mChildren = new RCollectionReview<>();
    }

    ReviewTree(Review root, RCollectionReview<ReviewNode> children, boolean isRatingAverage) {
        mReview = root;
        mChildren = children;
        mIsRatingAverage = isRatingAverage;
        if (mIsRatingAverage) mAverageRating = new MdRating(RatingAverager.average(this), this);
    }

    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public ReviewNode getParent() {
        return null;
    }

    @Override
    public RCollectionReview<ReviewNode> getChildren() {
        return mChildren;
    }

    @Override
    public boolean isRatingIsAverageOfChildren() {
        return mIsRatingAverage;
    }

    @Override
    public RCollectionReview<ReviewNode> flattenTree() {
        TraverserReviewNode traverser = new TraverserReviewNode(this);
        VisitorNodeCollector collector = new VisitorNodeCollector();
        traverser.setVisitor(collector);
        traverser.traverse();

        return collector.get();
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
        visitorReviewNode.visit(this);
    }

    @Override
    public ReviewId getId() {
        return mReview.getId();
    }

    @Override
    public MdSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public MdRating getRating() {
        return mIsRatingAverage ? mAverageRating : mReview.getRating();
    }

    @Override
    public ReviewNode getReviewNode() {
        return this;
    }

    @Override
    public Author getAuthor() {
        return mReview.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public MdCommentList getComments() {
        return mReview.getComments();
    }

    @Override
    public boolean hasComments() {
        return mReview.hasComments();
    }

    @Override
    public MdFactList getFacts() {
        return mReview.getFacts();
    }

    @Override
    public boolean hasFacts() {
        return mReview.hasFacts();
    }

    @Override
    public MdImageList getImages() {
        return mReview.getImages();
    }

    @Override
    public boolean hasImages() {
        return mReview.hasImages();
    }

    @Override
    public MdUrlList getUrls() {
        return mReview.getUrls();
    }

    @Override
    public boolean hasUrls() {
        return mReview.hasUrls();
    }

    @Override
    public MdLocationList getLocations() {
        return mReview.getLocations();
    }

    @Override
    public boolean hasLocations() {
        return mReview.hasLocations();
    }
}
