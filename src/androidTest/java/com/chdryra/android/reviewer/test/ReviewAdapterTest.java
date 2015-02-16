/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewAdapter;
import com.chdryra.android.reviewer.test.TestUtils.ControllerTester;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//Has to extend AndroidTestCase as fails in batch test with TestCase....
public class ReviewAdapterTest extends AndroidTestCase {
    private Review                   mReview;
    private ReviewAdapter<Review>    mController;
    private ControllerTester<Review> mTester;

    @SmallTest
    public void testGetId() {
        assertEquals(mReview.getId().toString(), mController.getId());
    }

    @SmallTest
    public void testGetSubject() {
        mTester.testGetSubject();
    }

    @SmallTest
    public void testGetRating() {
        mTester.testGetRating();
    }

    @SmallTest
    public void testGetAuthor() {
        mTester.testGetAuthor();
    }

    @SmallTest
    public void testGetPublishDate() {
        mTester.testGetPublishDate();
    }

    @SmallTest
    public void testHasData() {
        mTester.testHasData();
    }

    @SmallTest
    public void testGetData() {
        mTester.testGetData();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mReview = ReviewMocker.newReview();
        mController = new ReviewAdapter<>(mReview);
        mTester = new ControllerTester<>(mController, mReview);
    }
}
