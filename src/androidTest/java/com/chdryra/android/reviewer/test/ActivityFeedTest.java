/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.ControllerReviewTreeEditable;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.test.TestUtils.RatingMocker;
import com.chdryra.android.testutils.RandomStringGenerator;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityFeedTest extends ActivityViewReviewTest {
    private static final int NEWREVIEW = R.id.menu_item_new_review;
    private static final int NUM       = 5;
    private static final int TIMEOUT   = 10000;
    private Administrator mAdmin;

    public ActivityFeedTest() {
        super(GvDataList.GvType.FEED, false);
    }

    @SmallTest
    public void testFeed() {
        GvReviewList list = (GvReviewList) mAdmin.getPublishedReviews().toGridViewable();
        assertEquals(NUM, getGridSize());
        GvReviewList.GvReviewOverview oldReview = (GvReviewList.GvReviewOverview) getGridItem(0);
        for (int i = 0; i < NUM; ++i) {
            GvReviewList.GvReviewOverview review = (GvReviewList.GvReviewOverview) getGridItem(i);
            int j = list.size() - i - 1;
            assertEquals(list.getItem(j).getSubject(), review.getSubject());
            assertEquals(list.getItem(j).getRating(), review.getRating());
            assertNotNull(review.getPublishDate());
            if (i > 0) {
                assertTrue(oldReview.getPublishDate().after(review.getPublishDate()));
            }
            oldReview = review;
        }
    }

    @SmallTest
    public void testMenuNewReview() {
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor
                (ActivityViewReview.class.getName(), null, false);

        mSolo.clickOnActionBarItem(NEWREVIEW);
        getInstrumentation().waitForIdleSync();
        ActivityViewReview buildActivity = (ActivityViewReview) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(buildActivity);
        assertEquals(ActivityViewReview.class, buildActivity.getClass());
    }

    @Override
    protected ControllerReview getController() {
        ControllerReviewTreeEditable controller = mAdmin.createNewReviewInProgress();

        controller.setRating(RatingMocker.nextRating());
        controller.setSubject(RandomStringGenerator.nextWord());

        return controller;
    }

    @Override
    protected void setUp() {
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        ArrayList<ControllerReview> controllers = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            controllers.add(getController());
            mAdmin.publishReviewInProgress();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GvReviewList list = (GvReviewList) mAdmin.getPublishedReviews().toGridViewable();
        assertEquals(NUM, list.size());

        for (int i = 0; i < NUM; ++i) {
            assertEquals(controllers.get(i).getSubject(), list.getItem(i).getSubject());
            assertEquals(controllers.get(i).getRating(), list.getItem(i).getRating());
            assertNotNull(list.getItem(i).getPublishDate());
        }

        super.setUp();
    }

    @Override
    public void testSubjectRating() {

    }
}
