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
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

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

    public static Review createReviewUser(ReviewId.ReviewPublisher publisher, String subject,
            float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations,
            IdableList<Review> criteria, boolean ratingIsAverage) {
        return getInstance().newReviewUser(publisher, subject, rating, comments,
                images, facts, locations, criteria, ratingIsAverage);
    }

    public static Review createReviewUser(ReviewId.ReviewPublisher publisher, String subject,
            float rating) {
        return getInstance().newReviewUser(publisher, subject, rating);
    }

    public static ReviewTreeNode createReviewTreeNode(Review review, boolean isAverage) {
        return getInstance().newReviewTreeNode(review, isAverage);
    }

    //Constructors
    private Review newReviewUser(ReviewId.ReviewPublisher publisher, String subject, float
            rating) {
        ReviewId id = publisher.nextId();
        return new ReviewUser(id, publisher.getAuthor(), publisher.getDate(), subject, rating,
                new MdCommentList(id), new MdImageList(id), new MdFactList(id),
                new MdLocationList(id), new IdableList<Review>(), false);
    }

    private Review newReviewUser(ReviewId.ReviewPublisher publisher, String subject, float rating,
            Iterable<? extends DataComment> comments,
            Iterable<? extends DataImage> images,
            Iterable<? extends DataFact> facts,
            Iterable<? extends DataLocation> locations,
            IdableList<Review> criteria, boolean ratingIsAverage) {
        return new ReviewUser(publisher.nextId(), publisher.getAuthor(), publisher.getDate(),
                subject, rating, comments, images, facts, locations, criteria, ratingIsAverage);
    }

    private ReviewTreeNode newReviewTreeNode(Review review, boolean isAverage) {
        return new ReviewTreeNode(review, isAverage, review.getId());
    }
}
