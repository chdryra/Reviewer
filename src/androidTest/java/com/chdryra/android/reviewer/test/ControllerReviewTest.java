/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;

import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.test.TestUtils.ControllerTester;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
//Has to extend AndroidTestCase as fails in batch test with TestCase....
public class ControllerReviewTest extends AndroidTestCase {
    private Review                   mReview;
    private ControllerReview<Review> mController;
    private ControllerTester<Review> mTester;

    public void testGetReviewNode() {
        assertNotNull(mController.getReviewNode());
    }

    public void testGetId() {
        assertEquals(mReview.getId().toString(), mController.getId());
    }

    public void testGetSubject() {
        mTester.testGetSubject();
    }

    public void testGetRating() {
        mTester.testGetRating();
    }

    public void testGetAuthor() {
        mTester.testGetAuthor();
    }

    public void testGetPublishDate() {
        mTester.testGetPublishDate();
    }

    public void testIsPublished() {
        mTester.testIsPublished();
    }

    public void testAddAndGetTags() {
        mTester.testAddAndGetTags();
    }

    public void testRemoveTags() {
        mTester.testRemoveTags();
    }

    public void testHasData() {
        mTester.testHasData();
    }

    public void testGetData() {
        mTester.testGetData();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mReview = ReviewMocker.newReview();
        mController = new ControllerReview<>(mReview);
        mTester = new ControllerTester<>(mController, mReview);
    }
}
