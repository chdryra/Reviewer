/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditLocationMap;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditLocationsTest extends ActivityEditScreenTest<GvLocation> {
    protected static final int NUM_DATA = 3;
    private static final GvLocation TAYYABS =
            new GvLocation(new LatLng(51.517264, -0.063484), "Tayyabs");
    private static final String TAYYABSADD = "Fieldgate Street, London, " +
            "United Kingdom";
    private static final GvLocation TOWERBRIDGE =
            new GvLocation(new LatLng(51.50418459999999, -0.07632209999999999),
                    "Tower Bridge");
    private static final String TOWERBRIDGEADD = "Tower Bridge, London, " +
            "United Kingdom";
    private static final GvLocation DISHOOM =
            new GvLocation(new LatLng(51.51243, -0.126909), "Dishoom");
    private static final String DISHOOMADD = "Upper St Martin's Lane, " +
            "London, United Kingdom";
    private static final String[] LOCSADD = {TAYYABSADD, TOWERBRIDGEADD,
            DISHOOMADD};
    private static final int TIMEOUT = 10000;
    private final GvLocationList mLocs;

    private Instrumentation.ActivityMonitor mMapMonitor;
    private Instrumentation.ActivityMonitor mMainMonitor;

    //Constructors
    public ActivityEditLocationsTest() {
        super(GvLocation.TYPE);
        mLocs = new GvLocationList();
        mLocs.add(TAYYABS);
        mLocs.add(TOWERBRIDGE);
        mLocs.add(DISHOOM);
    }

    public void testLongClickBannerButtonShowsMap() {
        setUp(false);

        GvLocation here = new GvLocation(new LatLng(0, 0), "here");
        final GvLocationList data = new GvLocationList();
        data.add(here);

        checkInBuilders(data, false);
        checkInGrid(data, false);
        checkMapIsShowing(false);
        String alert = getInstrumentation().getTargetContext().getResources().getString(R.string
                .alert_add_on_map);
        assertFalse(mSolo.searchText(alert));

        mSolo.clickLongOnText("Add " + mDataType.getDatumName());

        mSolo.waitForDialogToOpen(TIMEOUT);
        assertTrue(mSolo.searchText(alert));

        runOnUiThread(new Runnable() {
            //Overridden
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

        waitForMapToLaunch();
        checkMapIsShowing(true);

        SoloDataEntry.enter(mSolo, data.getItem(0));

        clickMenuDone();
        waitForMapToClose();

        checkFragmentSubjectRatingAsExpected();
        checkBuildersSubjectRatingAsExpected();

        //Need to copy maps found location.
        GvLocation loc = getGridItem(0);
        GvLocationList testData = new GvLocationList();
        testData.add(new GvLocation(loc.getLatLng(), here.getName()));

        checkInGrid(testData, true);
        checkInBuilder(testData, true);
        checkInParentBuilder(testData, false);

        clickMenuDone();

        checkBuildersSubjectRatingOnDone();
        checkInBuilder(testData, true);
        checkInParentBuilder(testData, true);
    }

    @SmallTest
    public void testLongClickGridItemEditOnMap() {
        testLongClickGridItemEditOnMap(false);
    }

    @SmallTest
    public void testLongClickGridItemDeleteOnMap() {
        testLongClickGridItemEditOnMap(true);
    }

    protected void waitForMapToClose() {
        ActivityReviewView mainActivity = (ActivityReviewView)
                mMainMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(mainActivity);
        assertEquals(ActivityReviewView.class, mainActivity.getClass());
        getInstrumentation().waitForIdleSync();
    }

    protected void checkMapIsShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchText(mDataType.getDatumName()));
            assertFalse(mSolo.searchText(mDataType.getDataName()));
        } else {
            assertTrue(mSolo.searchText(mDataType.getDataName()));
        }
    }

    //private methods
    private DialogAlertFragment getAlertDialog() {
        FragmentManager manager = getEditActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogAlertFragment.ALERT_TAG);
        return (DialogAlertFragment) f;
    }

    private void waitForMapToLaunch() {
        ActivityEditLocationMap mapActivity = (ActivityEditLocationMap)
                mMapMonitor.waitForActivityWithTimeout(TIMEOUT);
        assertNotNull(mapActivity);
        assertEquals(ActivityEditLocationMap.class, mapActivity.getClass());
        getInstrumentation().waitForIdleSync();
    }

    private void testLongClickGridItemEditOnMap(boolean delete) {
        setUp(true);

        String alert = getInstrumentation().getTargetContext().getResources().getString(R.string
                .alert_edit_on_map);

        assertFalse(mSolo.searchText(alert));
        checkMapIsShowing(false);

        GvLocation clickedOn = getGridItem(0);
        clickLongOnGridItem(0);

        mSolo.waitForDialogToOpen(TIMEOUT);
        assertTrue(mSolo.searchText(alert));

        runOnUiThread(new Runnable() {
//Overridden
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

        waitForMapToLaunch();
        checkMapIsShowing(true);

        assertEquals(clickedOn.getName(), mSolo.getEditText(0).getText().toString());

        String newName = RandomString.nextWord();

        if (!delete) {
            mSolo.clearEditText(0);
            mSolo.enterText(0, newName);

            clickMenuDone();
        } else {
            clickMenuDelete();
            mSolo.waitForDialogToOpen(TIMEOUT);
            mSolo.sleep(2000);
            mSolo.clickOnButton("Yes");
            //For some reason the below works in debug but not otherwise....
//            final DialogAlertFragment mapAlert = getMapAlertDialog();
//            assertNotNull(mapAlert);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mSignaler.reset();
//                    mapAlert.clickPositiveButton();
//                    mSignaler.signal();
//                }
//            });
//
//            mSignaler.waitForSignal();
            mSolo.waitForDialogToClose(TIMEOUT);
        }

        waitForMapToClose();
        checkMapIsShowing(false);

        GvLocation testData = delete ? clickedOn
                : new GvLocation(clickedOn.getLatLng(), newName);

        checkInGrid(testData, !delete);
    }

    //Overridden
    @Override
    protected void setUpFinish(boolean withData) {
        super.setUpFinish(withData);
        mMainMonitor = getInstrumentation().addMonitor(ActivityReviewView.class.getName(), null,
                false);
        mMapMonitor = getInstrumentation().addMonitor(ActivityEditLocationMap.class.getName(),
                null, false);
    }

    @Override
    protected GvLocation newEditDatum(GvLocation oldDatum) {
        return new GvLocation(oldDatum.getLatLng(), RandomString.nextWord());
    }

    @Override
    protected GvDataListImpl<GvLocation> newData() {
        return mLocs;
    }

    @Override
    protected void enterDatum(GvDataList data, int index) {
        assertTrue(index < NUM_DATA);
        SoloDataEntry.enter(mSolo, (GvData) data.getItem(index));
        mSolo.waitForText(LOCSADD[index]);
        mSolo.clickInList(1);
        mSolo.sleep(3000);
    }
}


