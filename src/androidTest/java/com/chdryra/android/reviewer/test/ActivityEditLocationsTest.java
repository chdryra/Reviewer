/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityEditLocationMap;
import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.RandomStringGenerator;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditLocationsTest extends ActivityEditScreenTest {
    private static final int TIMEOUT = 10000;
    private Instrumentation.ActivityMonitor mMapMonitor;
    private Instrumentation.ActivityMonitor mMainMonitor;

    public ActivityEditLocationsTest() {
        super(GvDataList.GvType.LOCATIONS);
    }

    @SmallTest
    public void testForDebugging() {
//        super.testActivityLaunches();
        super.testBannerButtonAddDone();
//        super.testSubjectRatingChange();
//        super.testGridItemEditCancel();
//        super.testGridItemEditDone();
//        super.testGridItemDeleteConfirm();
//        super.testGridItemDeleteCancel();
//        super.testMenuDeleteConfirm();
//        super.testMenuDeleteCancel();
//        super.testMenuUpCancels();
    }

    @Override
    protected void setUpFinish(boolean withData) {
        super.setUpFinish(withData);
        mMainMonitor = getInstrumentation().addMonitor(ActivityViewReview.class.getName(), null,
                false);
        mMapMonitor = getInstrumentation().addMonitor(ActivityEditLocationMap.class.getName(),
                null, false);
    }

    @Override
    protected GvDataList getAddDataToTestAgainst(GvDataList data) {
        if (data.size() == 0) return super.getAddDataToTestAgainst(data);
        //Random data created won't match up with current location found by map activity.
        assertEquals(1, data.size());
        LatLng location = ((GvLocationList.GvLocation) getGridItem(0)).getLatLng();
        String name = ((GvLocationList.GvLocation) data.getItem(0)).getName();
        GvLocationList list = new GvLocationList();
        list.add(new GvLocationList.GvLocation(location, name));

        return list;
    }

    @Override
    protected GvDataList.GvData newEditDatum(GvDataList.GvData oldDatum) {
        GvLocationList.GvLocation location = (GvLocationList.GvLocation) oldDatum;
        return new GvLocationList.GvLocation(location.getLatLng(), RandomStringGenerator.nextWord
                ());
    }

    @Override
    protected GvDataList newData() {
        return GvDataMocker.getData(mDataType, 1);
    }

    @Override
    protected void waitForLaunchableToLaunch() {
        ActivityEditLocationMap mapActivity = (ActivityEditLocationMap)
                mMapMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(mapActivity);
        assertEquals(ActivityEditLocationMap.class, mapActivity.getClass());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    protected void waitForLaunchableToClose() {
        ActivityViewReview mainActivity = (ActivityViewReview)
                mMainMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(mainActivity);
        assertEquals(ActivityViewReview.class, mainActivity.getClass());
        getInstrumentation().waitForIdleSync();
    }

    @Override
    protected void testLaunchableShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchText(mDataType.getDatumString()));
            assertFalse(mSolo.searchText(mDataType.getDataString()));
        } else {
            assertTrue(mSolo.searchText(mDataType.getDataString()));
        }
    }

    @Override
    protected void clickEditConfirm() {
        clickMenuDone();
    }

    @Override
    protected void clickEditDelete() {
        clickMenuDelete();
    }

    @Override
    protected void clickEditCancel() {
        clickMenuUp();
    }

    @Override
    protected void clickAddConfirm() {
        clickMenuDone();
    }

    @Override
    protected void clickAddCancel() {
        clickMenuUp();
    }

    @Override
    protected Activity getEditActivity() {
        Activity activity = mMapMonitor.getLastActivity();
        if (activity == null) activity = mActivity;

        return activity;
    }
}


