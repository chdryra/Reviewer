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

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.TestDatabaseApplicationContext;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryShareScreenView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityUsersFeed;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
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
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityUsersFeed.class
                .getName(), null, false);

        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_publish));
        getInstrumentation().waitForIdleSync();

        ActivityUsersFeed feedActivity = (ActivityUsersFeed) monitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(feedActivity);
        assertEquals(ActivityUsersFeed.class, feedActivity.getClass());
        feedActivity.finish();
    }

    //protected methods
    @Override
    protected ReviewView getView() {
        FactoryShareScreenView builder = new FactoryShareScreenView();
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
        tagBulder.publishData();
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
        mAdmin = ApplicationInstance.newInstance(context, testContext);
        super.setUp();
    }
}
