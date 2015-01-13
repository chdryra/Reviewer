/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 January, 2015
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.PublishedReviews;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishedReviewsTest extends TestCase {
    public void testPublishedReviews() {
        PublishedReviews reviews = new PublishedReviews();
        RCollectionReview<ReviewNode> published = new RCollectionReview<>();
        for (int i = 0; i < 10; ++i) {
            ReviewNode r = ReviewMocker.newReviewPublished().getReviewNode();
            published.add(r);
            reviews.add(r);
        }

        for (int i = 0; i < 10; ++i) {
            ReviewNode r = ReviewMocker.newReviewNode();
            reviews.add(r);
        }

        GvReviewList gvReviews = (GvReviewList) reviews.toGridViewable();
        assertEquals(published.size(), gvReviews.size());

        for (int i = 0; i < published.size(); ++i) {
            assertEquals(published.getItem(i).getSubject().get(), gvReviews.getItem(i).getSubject
                    ());
            assertEquals(published.getItem(i).getRating().get(), gvReviews.getItem(i).getRating());
        }
    }
}
