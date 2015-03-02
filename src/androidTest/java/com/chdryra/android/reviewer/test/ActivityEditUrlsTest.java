/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Instrumentation;

import com.chdryra.android.reviewer.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvUrlList;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * This is just a placeholder test until I've decided how to introduce links to reviews.
 */
public class ActivityEditUrlsTest extends ActivityEditScreenTest {
    private static final int TIMEOUT = 10000;
    private Instrumentation.ActivityMonitor mBrowserMonitor;
    private Instrumentation.ActivityMonitor mMainMonitor;

    public ActivityEditUrlsTest() {
        super(GvUrlList.TYPE);
    }

    @Override
    protected void setUpFinish(boolean withData) {
        super.setUpFinish(withData);
        mMainMonitor = getInstrumentation().addMonitor(ActivityReviewView.class.getName(), null,
                false);
        mBrowserMonitor = getInstrumentation().addMonitor(ActivityEditUrlBrowser.class.getName(),
                null, false);
    }

    @Override
    protected GvDataList newData() {
        GvUrlList list = new GvUrlList();
        try {
            GvUrlList.GvUrl url = new GvUrlList.GvUrl("http://news.bbc.co.uk");
            list.add(url);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected void waitForLaunchableToLaunch() {
        ActivityEditUrlBrowser browserActivity = (ActivityEditUrlBrowser)
                mBrowserMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(browserActivity);
        assertEquals(ActivityEditUrlBrowser.class, browserActivity.getClass());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    protected void waitForLaunchableToClose() {
        ActivityReviewView mainActivity = (ActivityReviewView)
                mMainMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(mainActivity);
        assertEquals(ActivityReviewView.class, mainActivity.getClass());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    protected void checkLaunchableShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchText(mDataType.getDatumName()));
            assertFalse(mSolo.searchText(mDataType.getDataName()));
        } else {
            assertTrue(mSolo.searchText(mDataType.getDataName()));
        }
    }

    @Override
    protected Activity getEditActivity() {
        Activity activity = mBrowserMonitor.getLastActivity();
        if (activity == null) activity = mActivity;

        return activity;
    }
}
