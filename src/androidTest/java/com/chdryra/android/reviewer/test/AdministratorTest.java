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
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvSocialPlatformList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewCollectionAdapter;
import com.chdryra.android.reviewer.ViewReviewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdministratorTest extends AndroidTestCase {
    private Administrator mAdmin;

    @SmallTest
    public void testCreateNewReviewInProgress() {
        ViewReviewAdapter review = mAdmin.getNewReviewBuilder();
        assertNotNull(review);
    }

    @SmallTest
    public void testGetPublishedReviews() {
        GvDataList reviews = mAdmin.getPublishedReviews().toGridViewable();
        assertNotNull(reviews);
    }

    @SmallTest
    public void testPublishReviewInProgress() {
        ReviewCollectionAdapter<Review> reviews = mAdmin.getPublishedReviews();
        assertNotNull(reviews);
        assertEquals(0, reviews.toGridViewable().size());
        mAdmin.getNewReviewBuilder();
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
        ViewReviewAdapter review = mAdmin.getNewReviewBuilder();
        mAdmin.pack(review, i);
        ViewReviewAdapter unpacked = mAdmin.unpack(i);
        assertEquals(review, unpacked);
    }

    @SmallTest
    public void testPackUnpackBundle() {
        ViewReviewAdapter review = mAdmin.getNewReviewBuilder();
        Bundle args = mAdmin.pack(review);
        ViewReviewAdapter unpacked = mAdmin.unpack(args);
        assertEquals(review, unpacked);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdmin = Administrator.get(getContext());
        assertNotNull(mAdmin);
    }
}
