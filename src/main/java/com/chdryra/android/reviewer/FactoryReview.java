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

    public static Review createReviewUser(Author author, Date publishDate, String subject,
            float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations,
            Iterable<? extends DataUrl> urls) {
        return getInstance().newReviewUser(author, publishDate, subject, rating, comments,
                images, facts, locations, urls);
    }

    //Nodes
    public static ReviewNode createReviewNode(Review review) {
        return getInstance().newReviewNode(review);
    }

    public static ReviewNode createReviewTree(Review review,
            RCollectionReview<Review> children, boolean isAverage) {

        return getInstance().newReviewTree(review, children, isAverage);
    }

    //Constructors
    private Review newReviewUser(Author author, Date publishDate, String subject, float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations,
            Iterable<? extends DataUrl> urls) {
        return new ReviewUser(author, publishDate, subject, rating, comments, images, facts,
                locations, urls);
    }

    private ReviewNode newReviewNode(Review review) {
        return new ReviewTree(review);
    }

    private ReviewTree newReviewTree(Review parent, RCollectionReview<Review> children,
            boolean isAverage) {
        RCollectionReview<ReviewNode> childNodes = new RCollectionReview<>();
        for (Review child : children) {
            childNodes.add(createReviewNode(child));
        }
        return new ReviewTree(parent, childNodes, isAverage);
    }
}
