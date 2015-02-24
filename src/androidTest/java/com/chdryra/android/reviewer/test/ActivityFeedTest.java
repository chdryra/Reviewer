/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.GridView;

import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewBuilder;
import com.chdryra.android.reviewer.ReviewViewAdapter;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityFeedTest extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int NEWREVIEW = R.id.menu_item_new_review;
    private static final int NUM       = 5;
    private static final int TIMEOUT   = 10000;
    protected ReviewViewAdapter mAdapter;
    protected Activity          mActivity;
    protected Solo              mSolo;
    private   Administrator     mAdmin;

    public ActivityFeedTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testFeed() {
        GvReviewList list = (GvReviewList) mAdmin.getPublishedReviews().getGridData();
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
                (ActivityReviewView.class.getName(), null, false);

        mSolo.clickOnActionBarItem(NEWREVIEW);
        getInstrumentation().waitForIdleSync();
        ActivityReviewView buildActivity = (ActivityReviewView) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(buildActivity);
        assertEquals(ActivityReviewView.class, buildActivity.getClass());
    }

    @Override
    protected void setUp() {
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        mAdapter = mAdmin.getPublishedReviews();

        if (mAdapter.getGridData().size() == 0) {
            for (int i = 0; i < NUM; ++i) {
                ReviewBuilder builder = mAdmin.newReviewBuilder();
                builder.setSubject(RandomString.nextWord());
                builder.setRating(RandomRating.nextRating());
                mAdmin.publishReviewBuilder();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mAdapter = mAdmin.getPublishedReviews();
        }

        Intent i = new Intent();
        ActivityReviewView.packParameters(GvDataList.GvType.FEED, false, i);
        setActivityIntent(i);
        mActivity = getActivity();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    private GvDataList.GvData getGridItem(int position) {
        return (GvDataList.GvData) getGridView().getItemAtPosition(position);
    }

    private GridView getGridView() {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        return (GridView) views.get(0);
    }

    private int getGridSize() {
        return getGridView().getAdapter().getCount();
    }
}
