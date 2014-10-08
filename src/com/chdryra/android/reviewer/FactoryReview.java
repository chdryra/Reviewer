/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * Not a "classic" factory that returns top level interfaces/classes, but a convenient place to
 * put constructors so as to minimise the use of constructors in multiple places. Plus the range
 * of return types is relatively small so not too cumbersome.
 * </p>
 */
class FactoryReview {

    private static FactoryReview sFactory = null;

    private FactoryReview() {
    }

    private static FactoryReview getInstance() {
        if (sFactory == null) {
            sFactory = new FactoryReview();
        }

        return sFactory;
    }

    //Reviews
    public static ReviewTreeEditable createReviewInProgress() {
        return createReviewInProgress("");
    }

    public static ReviewTreeEditable createReviewInProgress(String subject) {
        return getInstance().newReviewTreeEditable(subject);
    }

    public static ReviewEditable createReviewEditable(String subject) {
        return getInstance().newReviewUserEditable(subject);
    }

    public static Review createReviewUser(ReviewUserEditable reviewEditable) {
        return getInstance().newReviewUser(reviewEditable);
    }

    public static Review createReview(ReviewNode reviewNode) {
        return getInstance().newReview(reviewNode);
    }

    public static ReviewEditable createNullReview() {
        return getInstance().newNullReview();
    }

    //Nodes
    public static ReviewNode createNullReviewNode() {
        return createReviewNodeAlone(createNullReview());
    }

    public static ReviewNodeExpandable createReviewNodeExpandable(Review review) {
        return getInstance().newReviewNodeExpandable(review);
    }

    public static ReviewNode createReviewNodeAlone(Review review) {
        return getInstance().newReviewNodeAlone(review);
    }

    //Constructors
    private ReviewTreeEditable newReviewTreeEditable(String subject) {
        return new ReviewTreeEditable(subject);
    }

    private ReviewEditable newReviewUserEditable(String subject) {
        return new ReviewUserEditable(subject);
    }

    private Review newReviewUser(ReviewUserEditable reviewEditable) {
        return new ReviewUser(reviewEditable);
    }

    private Review newReview(ReviewNode node) {
        return new ReviewTree(node);
    }

    private ReviewEditable newNullReview() {
        return new ReviewNull();
    }

    private ReviewNodeExpandable newReviewNodeExpandable(Review review) {
        return new ReviewComponent(review);
    }

    private ReviewNode newReviewNodeAlone(Review review) {
        return new ReviewNodeAlone(review);
    }
}
