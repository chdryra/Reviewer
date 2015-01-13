/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvSocialPlatformList;
import com.chdryra.android.reviewer.PublishedReviews;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdministratorTest extends AndroidTestCase {
    private static final String APPNAME = "Reviewer";
    private Administrator mAdmin;

    @SmallTest
    public void testCreateNewReviewInProgress() {
        ControllerReview review = mAdmin.createNewReviewInProgress();
        assertNotNull(review);
    }

    @SmallTest
    public void testGetPublishedReviews() {
        GvDataList reviews = mAdmin.getPublishedReviews().toGridViewable();
        assertNotNull(reviews);
    }

    @SmallTest
    public void testPublishReviewInProgress() {
        PublishedReviews reviews = mAdmin.getPublishedReviews();
        assertNotNull(reviews);
        assertEquals(0, reviews.toGridViewable().size());
        mAdmin.createNewReviewInProgress();
        mAdmin.publishReviewInProgress();
        assertEquals(1, reviews.toGridViewable().size());
    }

    @SmallTest
    public void testGetSocialPlatformList() {
        GvSocialPlatformList list = mAdmin.getSocialPlatformList();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @SmallTest
    public void testPackUnpackIntent() {
        Intent i = new Intent();
        ControllerReview review = mAdmin.createNewReviewInProgress();
        mAdmin.pack(review, i);
        ControllerReview unpacked = mAdmin.unpack(i);
        assertEquals(review, unpacked);
    }

    @SmallTest
    public void testPackUnpackBundle() {
        ControllerReview review = mAdmin.createNewReviewInProgress();
        Bundle args = mAdmin.pack(review);
        ControllerReview unpacked = mAdmin.unpack(args);
        assertEquals(review, unpacked);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdmin = Administrator.get(getContext());
        assertNotNull(mAdmin);
    }
}
