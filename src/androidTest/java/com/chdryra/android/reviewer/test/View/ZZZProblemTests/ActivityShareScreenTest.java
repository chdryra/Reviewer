/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ZZZProblemTests;

import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewFeed;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityFeed;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Screens.ShareScreen;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens.ActivityReviewViewTest;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareScreenTest extends ActivityReviewViewTest {
    private static final int TIMEOUT = 10000;
    private SocialPlatformList mList;
    private Administrator      mAdmin;

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
        assertEquals(0, ReviewFeed.getFeedAdapter(getActivity()).getGridData().size());

        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_publish));
        getInstrumentation().waitForIdleSync();

        ActivityFeed feedActivity = (ActivityFeed) monitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(feedActivity);
        assertEquals(ActivityFeed.class, feedActivity.getClass());
        GvReviewOverviewList list = (GvReviewOverviewList) ReviewFeed.getFeedAdapter(getActivity
                ()).getGridData();
        assertEquals(1, list.size());
        assertEquals(mAdapter.getSubject(), list.getItem(0).getSubject());
        assertEquals(mAdapter.getRating(), list.getItem(0).getRating());
        feedActivity.finish();
    }

    @Override
    protected void setAdapter() {
        ReviewBuilder builder = mAdmin.newReviewBuilder();

        builder.setRating(RandomRating.nextRating());
        builder.setSubject(RandomString.nextWord());
        ReviewBuilder.DataBuilder tagBulder = builder.getDataBuilder(GvTagList.TYPE);
        for (GvTagList.GvTag tag : GvDataMocker.newTagList(3, false)) {
            tagBulder.add(tag);
        }
        tagBulder.setData();
        mAdapter = builder;
    }

    @Override
    protected ReviewView getView() {
        return ShareScreen.newScreen(getInstrumentation().getTargetContext());
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentReviewView fragment = getFragmentViewReview();
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
