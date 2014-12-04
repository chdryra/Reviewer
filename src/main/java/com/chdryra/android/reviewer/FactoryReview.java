/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * Factory for creating Reviews and ReviewNodes.
 * <p/>
 * <p>
 * Not a "classic" factory that returns top level interfaces/classes, but a convenient place to
 * put constructors so as to minimise the use of constructors in multiple places. Plus the range
 * of return types is relatively small so not too cumbersome.
 * </p>
 */
public class FactoryReview {

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

    public static Review createReview(ReviewNode reviewNode) {
        return getInstance().newReview(reviewNode);
    }

    public static Review createReview(Author author, Date publishDate, ReviewNode reviewNode) {
        return getInstance().newReview(author, publishDate, reviewNode);
    }

    //Nodes
    public static ReviewNode createReviewNodeAlone(Review review) {
        return getInstance().newReviewNodeAlone(review);
    }

    public static ReviewNodeExpandable createReviewNodeExpandable(Review review) {
        return getInstance().newReviewNodeExpandable(review);
    }

    //Constructors
    private ReviewTreeEditable newReviewTreeEditable(String subject) {
        return new ReviewTreeEditable(new ReviewUserEditable(subject));
    }

    private Review newReview(ReviewNode node) {
        return new ReviewTree(node.getAuthor(), node.getPublishDate(), node);
    }

    private Review newReview(Author author, Date publishDate, ReviewNode node) {
        return new ReviewTree(author, publishDate, node);
    }

    private ReviewNodeExpandable newReviewNodeExpandable(Review review) {
        return new ReviewNodeExpandableImpl(review);
    }

    private ReviewNode newReviewNodeAlone(Review review) {
        return new ReviewNodeAlone(review);
    }
}
