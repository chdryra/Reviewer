/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test.Controller;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.ActivityReviewView;
import com.chdryra.android.reviewer.View.FeedScreen;
import com.chdryra.android.reviewer.View.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.ImageChooser;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdministratorTest extends ActivityInstrumentationTestCase2<ActivityReviewView> {
    private Administrator mAdmin;

    public AdministratorTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testGetImageChooser() {
        Activity a = getActivity();
        ImageChooser im = Administrator.getImageChooser(a);
        assertNull(Administrator.getImageChooser(getActivity()));
        mAdmin.newReviewBuilder();
        assertNotNull(Administrator.getImageChooser(getActivity()));
    }

    @SmallTest
    public void testGetAuthor() {
        assertNotNull(mAdmin.getAuthor());
    }

    @SmallTest
    public void testNewReviewBuilder() {
        assertNotNull(mAdmin.newReviewBuilder());
    }

    @SmallTest
    public void testGetPublishedReviews() {
        assertNotNull(mAdmin.getPublishedReviews().getGridData());
    }

    @SmallTest
    public void testPublishReviewBuilder() {
        ReviewViewAdapter reviews = mAdmin.getPublishedReviews();
        assertNotNull(reviews);
        int numReviews = reviews.getGridData().size();
        mAdmin.newReviewBuilder();
        assertNotNull(mAdmin.getReviewBuilder());
        mAdmin.publishReviewBuilder();
        assertEquals(numReviews + 1, reviews.getGridData().size());
        assertNull(mAdmin.getReviewBuilder());
    }

    @SmallTest
    public void testGetSocialPlatformList() {
        GvSocialPlatformList list = mAdmin.getSocialPlatformList();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        assertNotNull(mAdmin);
        Intent i = new Intent();
        mAdmin.packView(FeedScreen.newScreen(getInstrumentation().getTargetContext()), i);
        setActivityIntent(i);
        Activity a = getActivity();
        assertNotNull(a);
        ImageChooser ic = Administrator.getImageChooser(a);
    }
}
