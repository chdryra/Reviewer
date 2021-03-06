/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.startouch.test.TestUtils;

import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewTreeMutable;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewStamp;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMocker {
    private static final int NUM = 3;

    private ReviewMocker() {
    }

    //Static methods
    public static Review newReview(ReviewStamp publisher) {
        return getNewReview(publisher, true);
    }

    public static Review newReview() {
        return getNewReview(true);
    }

    public static ReviewNode newReviewNode(boolean ratingIsAverage) {
        return getNewNode(ratingIsAverage);
    }

    private static ReviewNode getNewNode(boolean ratingIsAverage) {
        Review root = getNewReview(true);
        Review parent = getNewReview(false);
        ReviewTreeMutable rootNode = new ReviewTreeMutable(root, ratingIsAverage, root
                .getMdReviewId());
        ReviewTreeMutable parentNode = new ReviewTreeMutable(parent, false, parent.getMdReviewId());
        rootNode.setParent(parentNode);

        for (int i = 0; i < NUM; ++i) {
            Review review = getNewReview(true);
            rootNode.addChild(new ReviewTreeMutable(review, false, review.getMdReviewId()));
        }

        return rootNode;
    }

    private static Review getNewReview(boolean withCriteria) {
        return getNewReview(RandomPublisher.nextPublisher(), withCriteria);
    }

    private static Review getNewReview(ReviewStamp publisher, boolean withCriteria) {
        Review review;
        if (withCriteria) {
            review = new MockReview(publisher, getCriteria(publisher));
            ;
        } else {
            review = new MockReview(publisher, getCriteria(null));
        }

        return review;
    }

    private static MdIdableCollection<Review> getCriteria(ReviewStamp publisher) {
        MdIdableCollection<Review> criteria = new MdIdableCollection<>();
        if (publisher != null) {
            for (int i = 0; i < NUM; ++i) {
                criteria.add(new MockReview(publisher));
            }
        }

        return criteria;
    }

    static class MockReview extends ReviewUser {
        private MockReview(ReviewStamp publisher, MdIdableCollection<Review> criteria) {
            super(MdReviewId.newId(publisher), publisher.getAuthor(), publisher.getDate(),
                    RandomString.nextWord(),
                    RandomRating.nextRating(),
                    GvDataMocker.newCommentList(NUM, false),
                    GvDataMocker.newImageList(NUM, false),
                    GvDataMocker.newFactList(NUM, false),
                    GvDataMocker.newLocationList(NUM, false),
                    criteria, false);
        }

        private MockReview(ReviewStamp publisher) {
            super(MdReviewId.newId(publisher), publisher.getAuthor(), publisher.getDate(),
                    RandomString.nextWord(),
                    RandomRating.nextRating(),
                    GvDataMocker.newCommentList(0, false),
                    GvDataMocker.newImageList(0, false),
                    GvDataMocker.newFactList(0, false),
                    GvDataMocker.newLocationList(0, false),
                    new MdIdableCollection<Review>(), false);
        }
    }
}
