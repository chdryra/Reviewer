/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Author;
import com.chdryra.android.reviewer.FactoryReview;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewTreeNode;
import com.chdryra.android.reviewer.ReviewUser;
import com.chdryra.android.testutils.RandomString;

import java.util.Date;

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

    public static ReviewNode newReviewNode() {
        return getNewNode();
    }

    private static ReviewNode getNewNode() {
        Review root = new MockReview();
        Review parent = new MockReview();
        ReviewTreeNode rootNode = new ReviewTreeNode(root, false, false);
        ReviewTreeNode parentNode = new ReviewTreeNode(parent, false, false);
        rootNode.setParent(parentNode);

        for (int i = 0; i < NUM; ++i) {
            rootNode.addChild(new ReviewTreeNode(new MockReview(), false, false));
        }

        return rootNode;
    }

    private static Review getNewReview() {
        Review root = new MockReview();
        RCollectionReview<Review> children = new RCollectionReview<>();
        for (int i = 0; i < NUM; ++i) {
            children.add(new MockReview());
        }

        return FactoryReview.createReviewTree(root, children, false);
    }

    static class MockReview extends ReviewUser {
        private MockReview() {
            super(Author.NULL_AUTHOR, new Date(), RandomString.nextWord(),
                    RandomRating.nextRating(), GvDataMocker.newCommentList(NUM),
                    GvDataMocker.newImageList(NUM), GvDataMocker.newFactList(NUM),
                    GvDataMocker.newLocationList(NUM), GvDataMocker.newUrlList(NUM));
        }
    }
}
