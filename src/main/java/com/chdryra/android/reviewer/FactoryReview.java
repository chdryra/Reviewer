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
 * A convenient place to
 * put constructors so as to minimise the use of constructors in multiple places.
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
            Iterable<? extends DataLocation> locations) {
        return getInstance().newReviewUser(author, publishDate, subject, rating, comments,
                images, facts, locations);
    }

    public static Review createReviewUser(Author author, Date publishDate, String subject,
            float rating) {
        return getInstance().newReviewUser(author, publishDate, subject, rating);
    }

    public static ReviewNode createReviewNode(Review review) {
        return getInstance().newReviewNode(review);
    }

    public static ReviewNode createReviewTree(Review review,
            RCollectionReview<Review> children, boolean isAverage) {

        return getInstance().newReviewTree(review, children, isAverage);
    }

    public static ReviewNode createReviewCollection(Author author, Date publishDate,
            String subject, RCollectionReview<Review> children) {
        Review root = createReviewUser(author, publishDate, subject, 0);
        return createReviewTree(root, children, true);
    }

    //Constructors
    private Review newReviewUser(Author author, Date publishDate, String subject, float rating) {
        return new ReviewUser(ReviewId.generateId(), author, publishDate, subject, rating);
    }

    private Review newReviewUser(Author author, Date publishDate, String subject, float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations) {
        return new ReviewUser(ReviewId.generateId(), author, publishDate, subject, rating,
                comments, images, facts, locations);
    }

    private ReviewNode newReviewNode(Review review) {
        return newReviewTree(review, new RCollectionReview<Review>(), false);
    }

    private ReviewNode newReviewTree(Review root, RCollectionReview<Review> children,
            boolean isAverage) {

        ReviewTreeNode rootNode = new ReviewTreeNode(root, isAverage, false);
        for (Review child : children) {
            rootNode.addChild(new ReviewTreeNode(child, false, false));
        }

        return rootNode.createTree();
    }
}
