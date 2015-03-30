/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.RCollectionReview;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RCollectionReviewTest extends TestCase {
    private final static int NUM = 100;
    private RCollectionReview<Review> mCollection;

    @SmallTest
    public void testAdd() {
        assertEquals(0, mCollection.size());
        Review[] data = new Review[NUM];
        for (int i = 0; i < NUM; ++i) {
            data[i] = ReviewMocker.newReview();
            assertFalse(mCollection.containsId(data[i].getId()));
            mCollection.add(data[i]);
            assertTrue(mCollection.containsId(data[i].getId()));
            assertEquals(i + 1, mCollection.size());
        }

        for (int i = 0; i < NUM; ++i) {
            assertEquals(data[i], mCollection.getItem(i));
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCollection = new RCollectionReview<>();
    }
}
