/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.UserData.Author;

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

    public static Review createReviewUser(Author author, PublishDate publishDate, String subject,
            float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations) {
        return getInstance().newReviewUser(author, publishDate, subject, rating, comments,
                images, facts, locations);
    }

    public static Review createReviewUser(Author author, PublishDate publishDate, String subject,
            float rating) {
        return getInstance().newReviewUser(author, publishDate, subject, rating);
    }

    public static ReviewNode createReviewNode(Review review) {
        return getInstance().newReviewNode(review);
    }

    public static ReviewTreeNode createReviewTreeNode(Review review, boolean isAverage) {
        return getInstance().newReviewTreeNode(review, isAverage);
    }

    //Constructors
    private Review newReviewUser(Author author, PublishDate publishDate, String subject, float
            rating) {
        return new ReviewUser(ReviewId.generateId(author), author, publishDate, subject, rating);
    }

    private Review newReviewUser(Author author, PublishDate publishDate, String subject, float
            rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations) {
        return new ReviewUser(ReviewId.generateId(author), author, publishDate, subject, rating,
                comments, images, facts, locations);
    }

    private ReviewNode newReviewNode(Review review) {
        return newStaticTree(review, new ReviewIdableList<Review>(), false);
    }

    private ReviewTreeNode newReviewTreeNode(Review review, boolean isAverage) {
        return new ReviewTreeNode(review, isAverage, review.getId());
    }

    private <T extends Review> ReviewNode newStaticTree(Review review, ReviewIdableList<T>
            children, boolean isAverage) {
        return newDynamicTree(review, children, isAverage).createTree();
    }

    private <T extends Review> ReviewTreeNode newDynamicTree(Review review, ReviewIdableList<T>
            children, boolean isAverage) {

        ReviewTreeNode rootNode = new ReviewTreeNode(review, isAverage, review.getId());
        for (Review child : children) {
            rootNode.addChild(newReviewTreeNode(child, false));
        }

        return rootNode;
    }
}