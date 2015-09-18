/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewUser;
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

    public static Review newReview() {
        return getNewReview();
    }

    public static ReviewNode newReviewNode(boolean ratingIsAverage) {
        return getNewNode(ratingIsAverage);
    }

    private static ReviewNode getNewNode(boolean ratingIsAverage) {
        Review root = new MockReview(getCriteria(true));
        Review parent = new MockReview(getCriteria(false));
        ReviewTreeNode rootNode = new ReviewTreeNode(root, ratingIsAverage, root.getId());
        ReviewTreeNode parentNode = new ReviewTreeNode(parent, false, parent.getId());
        rootNode.setParent(parentNode);

        for (int i = 0; i < NUM; ++i) {
            Review review = new MockReview(getCriteria(true));
            rootNode.addChild(new ReviewTreeNode(review, false, review.getId()));
        }

        return rootNode;
    }

    private static Review getNewReview() {
        return new MockReview(getCriteria(true));
    }

    private static IdableList<Review> getCriteria(boolean nonZero) {
        IdableList<Review> criteria = new IdableList<>();
        if (nonZero) {
            criteria.add(new MockReview(new IdableList<Review>()));
        }

        return criteria;
    }

    static class MockReview extends ReviewUser {
        private MockReview(IdableList<Review> criteria) {
            super(RandomPublisher.nextPublisher(), RandomString.nextWord(),
                    RandomRating.nextRating(), GvDataMocker.newCommentList(NUM, false),
                    GvDataMocker.newImageList(NUM, false), GvDataMocker.newFactList(NUM, false),
                    GvDataMocker.newLocationList(NUM, false), criteria, false);
        }
    }
}
