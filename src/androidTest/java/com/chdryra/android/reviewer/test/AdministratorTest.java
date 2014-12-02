/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.GVReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdministratorTest extends AndroidTestCase {
    private static final String APPNAME = "Reviewer";
    private Administrator mAdmin;

    @SmallTest
    public void testGetApplicationName() {
        String name = mAdmin.getApplicationName();
        assertNotNull(name);
        assertEquals(APPNAME, name);
    }

    @SmallTest
    public void testCreateNewReviewInProgress() {
        ControllerReview review = mAdmin.createNewReviewInProgress();
        assertNotNull(review);
    }

    @SmallTest
    public void testGetPublishedReviews() {
        GVReviewOverviewList reviews = mAdmin.getPublishedReviewsData();
        assertNotNull(reviews);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdmin = Administrator.get(getContext());
        assertNotNull(mAdmin);
    }
}
