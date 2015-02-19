/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.FragmentViewReview;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvReviewList;
import com.chdryra.android.reviewer.GvSocialPlatformList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewBuilder;
import com.chdryra.android.reviewer.SocialPlatformList;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareScreenTest extends ActivityViewReviewTest {
    private static final int               TIMEOUT = 10000;
    private static final GvDataList.GvType TYPE    = GvDataList.GvType.SHARE;
    private SocialPlatformList mList;
    private Administrator      mAdmin;

    public ActivityShareScreenTest() {
        super(TYPE, false);
    }

    @SmallTest
    public void testPlatformNamesFollowers() {
        assertEquals(mList.size(), getGridSize());
        int i = 0;
        for (SocialPlatformList.SocialPlatform platform : mList) {
            GvSocialPlatformList.GvSocialPlatform gv = (GvSocialPlatformList.GvSocialPlatform)
                    getGridItem(i++);
            assertEquals(platform.getName(), gv.getName());
            assertEquals(platform.getFollowers(), gv.getFollowers());
        }
    }

    @SmallTest
    public void testSelection() {
        for (int i = 0; i < mList.size(); ++i) {
            GvSocialPlatformList.GvSocialPlatform platform = getPlatform(i);
            assertFalse(platform.isChosen());
            mSolo.clickInList(i + 1);
            mSolo.sleep(1000);
            assertTrue(platform.isChosen());
            mSolo.clickInList(i + 1);
            mSolo.sleep(1000);
            assertFalse(platform.isChosen());
        }
    }

    @SmallTest
    public void testPublishButton() {
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityFeed.class
                .getName(), null, false);
        assertEquals(0, mAdmin.getPublishedReviews().toGridViewable().size());

        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_publish));
        getInstrumentation().waitForIdleSync();

        ActivityFeed feedActivity = (ActivityFeed) monitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(feedActivity);
        assertEquals(ActivityFeed.class, feedActivity.getClass());
        GvReviewList list = (GvReviewList) mAdmin.getPublishedReviews().toGridViewable();
        assertEquals(1, list.size());
        assertEquals(mAdapter.getSubject(), list.getItem(0).getSubject());
        assertEquals(mAdapter.getRating(), list.getItem(0).getRating());
    }

    @Override
    protected void setAdapter() {
        ReviewBuilder builder = mAdmin.getNewReviewBuilder();

        builder.setRating(RandomRating.nextRating());
        builder.setSubject(RandomString.nextWord());

        mAdapter = builder;
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentViewReview fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    @Override
    protected void setUp() {
        mList = SocialPlatformList.getList(getInstrumentation().getTargetContext());
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        super.setUp();
    }

    private GvSocialPlatformList.GvSocialPlatform getPlatform(int index) {
        return (GvSocialPlatformList.GvSocialPlatform) getGridItem(index);
    }
}
