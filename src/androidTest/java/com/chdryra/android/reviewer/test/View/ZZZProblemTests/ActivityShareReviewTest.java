/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ZZZProblemTests;

import android.app.Instrumentation;
import android.content.Context;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.TestDatabaseApplicationContext;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityFeed;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSocialPlatform;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Builders.BuilderShareScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens.ActivityReviewViewTest;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReviewTest extends ActivityReviewViewTest {
    private static final int TIMEOUT = 10000;
    private SocialPlatformList mList;
    private ApplicationInstance mAdmin;

    @SmallTest
    public void testPlatformNamesFollowers() {
        assertEquals(mList.size(), getGridSize());
        int i = 0;
        for (SocialPlatform platform : mList) {
            GvSocialPlatform gv = getPlatform(i++);
            assertEquals(platform.getName(), gv.getName());
            assertEquals(platform.getFollowers(), gv.getFollowers());
        }
    }

    @SmallTest
    public void testSelection() {
        for (int i = 0; i < mList.size(); ++i) {
            GvSocialPlatform platform = getPlatform(i);
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

        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_publish));
        getInstrumentation().waitForIdleSync();

        ActivityFeed feedActivity = (ActivityFeed) monitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(feedActivity);
        assertEquals(ActivityFeed.class, feedActivity.getClass());
        feedActivity.finish();
    }

    //protected methods
    @Override
    protected ReviewView getView() {
        BuilderShareScreen builder = new BuilderShareScreen();
        return builder.buildView("ShareScreen", mList, (ReviewBuilderAdapter) mAdapter);
    }

    private GvSocialPlatform getPlatform(int index) {
        return (GvSocialPlatform) getGridItem(index);
    }

    //Overridden
    @Override
    protected void setAdapter() {
        ReviewBuilderAdapter builder = mAdmin.newReviewBuilder();

        builder.setRating(RandomRating.nextRating());
        builder.setSubject(RandomString.nextWord());
        DataBuilderAdapter<GvTag> tagBulder =
                builder.getDataBuilderAdapter(GvTag.TYPE);
        for (GvTag tag : GvDataMocker.newTagList(3, false)) {
            tagBulder.add(tag);
        }
        tagBulder.setData();
        mAdapter = builder;
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    @Override
    protected void setUp() {
        Context context = getInstrumentation().getTargetContext();
        mList = new SocialPlatformList(context);
        ApplicationContext testContext = new TestDatabaseApplicationContext(context);
        mAdmin = ApplicationInstance.createWithApplicationContext(context, testContext);
        super.setUp();
    }
}