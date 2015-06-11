/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditUrlBrowser;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 05/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditFactsTest extends ActivityEditScreenTest {
    private static final String BBC     = "BBC";
    private static final String BBC_URL = "http://www.bbc.co.uk/";

    private Instrumentation.ActivityMonitor mBrowserMonitor;
    private boolean mUrlData = false;

    public ActivityEditFactsTest() {
        super(GvFactList.TYPE);
    }

    public void testLongClickBannerButtonShowsBrowser() {
        setUp(false);

        checkBrowserIsShowing(false);
        String alert = getInstrumentation().getTargetContext().getResources().getString(R.string
                .alert_add_on_browser);
        assertFalse(mSolo.searchText(alert));

        mSolo.clickLongOnText("Add " + mDataType.getDatumName());

        mSolo.waitForDialogToOpen(TIMEOUT);
        assertTrue(mSolo.searchText(alert));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getAlertDialog().clickPositiveButton();
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();

        mSolo.waitForDialogToClose(TIMEOUT);
        assertFalse(mSolo.searchText(alert));

        Activity browser = waitForBrowserToLaunch();
        checkBrowserIsShowing(true);
        browser.finish();
        ;
    }

    @SmallTest
    public void testLongClickGridItemEditOnBrowser() {
        mUrlData = true;
        setUp(true);

        String alert = getInstrumentation().getTargetContext().getResources().getString(R.string
                .alert_edit_on_browser);

        assertFalse(mSolo.searchText(alert));
        checkBrowserIsShowing(false);

        clickLongOnGridItem(0);

        mSolo.waitForDialogToOpen(TIMEOUT);
        assertTrue(mSolo.searchText(alert));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getAlertDialog().clickPositiveButton();
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();

        mSolo.waitForDialogToClose(TIMEOUT);
        assertFalse(mSolo.searchText(alert));

        Activity browser = waitForBrowserToLaunch();
        checkBrowserIsShowing(true);
        browser.finish();
    }

    @SmallTest
    public void testBannerButtonAddUrl() {
        mUrlData = true;
        super.testBannerButtonAddDone();
        GvDataList builderData = getParentBuilder().getData(mDataType);
        GvFactList.GvFact url = (GvFactList.GvFact) builderData.getItem(0);
        assertTrue(url.isUrl());
    }

    protected void checkBrowserIsShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchText(GvUrlList.TYPE.getDatumName()));
        } else {
            assertTrue(mSolo.searchText(mDataType.getDataName()));
        }
    }

    @Override
    protected void setUpFinish(boolean withData) {
        super.setUpFinish(withData);
        mBrowserMonitor = getInstrumentation().addMonitor(ActivityEditUrlBrowser.class.getName(),
                null, false);
    }

    protected GvDataList newData() {
        if (mUrlData) {
            GvFactList urls = new GvFactList();
            try {
                urls.add(new GvUrlList.GvUrl(BBC, new URL(BBC_URL)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                fail("Couldn't create URL data");
            }
            return urls;
        } else {
            return super.newData();
        }
    }

    private DialogAlertFragment getAlertDialog() {
        FragmentManager manager = getEditActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogAlertFragment.ALERT_TAG);
        return (DialogAlertFragment) f;
    }

    private ActivityEditUrlBrowser waitForBrowserToLaunch() {
        ActivityEditUrlBrowser browser = (ActivityEditUrlBrowser)
                mBrowserMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(browser);
        assertEquals(ActivityEditUrlBrowser.class, browser.getClass());
        getInstrumentation().waitForIdleSync();

        return browser;
    }
}

