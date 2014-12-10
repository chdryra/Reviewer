/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Author;
import com.chdryra.android.reviewer.PublisherReviewTree;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewNodeExpandable;
import com.chdryra.android.reviewer.ReviewTreeEditable;
import com.chdryra.android.reviewer.ReviewUserEditable;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMocker {
    public static final String SUBJECT = "MockReviewEditable";
    public static final float  RATING  = 3.0f;


    private ReviewMocker() {
    }

    public static Review newReview() {
        return newReviewEditable();
    }

    public static Review newReviewPublished() {
        Review r = getNew().publish(new PublisherReviewTree(new Author("Rizwan Choudrey")));
        Assert.assertTrue(r.isPublished());
        return r;
    }

    public static ReviewEditable newReviewEditable() {
        return new MockReviewEditable();
    }

    public static ReviewNode newReviewNode() {
        return getNew().getReviewNode();
    }

    public static ReviewNodeExpandable newReviewNodeExpandable() {
        return (ReviewNodeExpandable) newReviewNode();
    }

    public static ReviewTreeEditable newReviewTreeEditable() {
        return getNew();
    }

    private static MockReview getNew() {
        return new MockReview();
    }

    static class MockReview extends ReviewTreeEditable {
        private MockReview() {
            super(new MockReviewEditable());
        }
    }

    static class MockReviewEditable extends ReviewUserEditable {
        private MockReviewEditable() {
            super(SUBJECT);
            setRating(RATING);
        }
    }
}
