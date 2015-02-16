/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewCollectionAdapter;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewCollectionAdapterTest extends TestCase {
    private static final int MIN = 10;
    private static final int MAX = 20;
    private ReviewCollectionAdapter<Review> mAdapter;

    @SmallTest
    public void testAddReview() {
        GvDataList list = mAdapter.toGridViewable();
        assertNotNull(list);
        assertEquals(0, list.size());

        Review[] reviews = addReviews(mAdapter, getRandInt());

        list = mAdapter.toGridViewable();
        assertNotNull(list);
        assertEquals(reviews.length, list.size());
    }

    @SmallTest
    public void testToGridViewable() {
        Review[] reviews = addReviews(mAdapter, getRandInt());
        GvReviewList oList = (GvReviewList) mAdapter.toGridViewable();
        assertNotNull(oList);
        assertEquals(reviews.length, oList.size());
        for (int i = 0; i < reviews.length; ++i) {
            assertEquals(reviews[i].getRating().get(), oList.getItem(i).getRating());
            assertEquals(reviews[i].getSubject().get(), oList.getItem(i).getSubject());
            assertEquals(reviews[i].getAuthor().getName(), oList.getItem(i).getAuthor());
            assertEquals(reviews[i].getPublishDate(), oList.getItem(i).getPublishDate());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdapter = new ReviewCollectionAdapter<>();
    }

    private Review[] addReviews(ReviewCollectionAdapter<Review> controller, int num) {
        Review[] reviews = new Review[num];
        for (int i = 0; i < num; ++i) {
            Review r = ReviewMocker.newReview();
            reviews[i] = r;
            controller.add(r);
        }

        return reviews;
    }

    private int getRandInt() {
        Random rand = new Random();
        return MIN + rand.nextInt(MAX - MIN);
    }
}
