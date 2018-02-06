/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 March, 2015
 */

package com.chdryra.android.startouch.test.View.Dialogs;

import android.location.Location;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.test.TestUtils.DialogAddListener;
import com.chdryra.android.testutils.CallBackSignaler;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddLocationTest extends DialogGvDataAddTest<GvLocation>
        implements LocationClientConnector.Locatable {
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
    private static final GvLocation[] LOCS = {TAYYABS, TOWERBRIDGE, DISHOOM};
    private static final String DISHOOMADD = "Upper St Martin's Lane, " +
            "London, United Kingdom";
    private static final String[] LOCSADD = {TAYYABSADD, TOWERBRIDGEADD,
            DISHOOMADD};

    private LatLng mCurrent;
    private CallBackSignaler mSignaler;
    private LocationClientConnector mLocater;

    //Constructors
    public DialogAddLocationTest() {
        super(UiAndroid.DefaultLaunchables.AddLocation.class);
    }

    private GvData enterDataAndTest(int index) {
        assertTrue(index < 3);
        assertTrue(isDataNulled());
        GvData data = LOCS[index];
        enterData(data);
        assertTrue(isDataEntered());
        mSolo.waitForText(LOCSADD[index]);
        mSolo.clickInList(1);
        mSolo.sleep(3000);

        return data;
    }

    private GvData testQuickSet(boolean pressAdd, int index) {
        final DialogAddListener<GvLocation> listener = mListener;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());
        GvData data = enterDataAndTest(index);
        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());

        if (pressAdd) {
            mActivity.runOnUiThread(new Runnable() {
                //Overridden
                public void run() {
                    dialog.clickAddButton();
                }
            });
        }

        assertFalse(mSignaler.timedOut());
        return data;
    }

    //Overridden
    @Override
    public void testQuickSet() {
        launchDialogAndTestShowing(true);

        assertEquals(0, getData(mAdapter).size());

        GvData datum1 = testQuickSet(true, 0);
        GvData datum2 = testQuickSet(true, 1);
        GvData datum3 = testQuickSet(false, 2);

        pressDialogButton(DialogButton.DONE);
        GvDataList data = getData(mAdapter);

        assertEquals(3, data.size());
        assertEquals(datum1, data.getItem(0));
        assertEquals(datum2, data.getItem(1));
        assertEquals(datum3, data.getItem(2));
    }

    protected GvData enterDataAndTest() {
        return enterDataAndTest(0);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLocater = new LocationClientConnector(getActivity(), this);
        mSignaler = new CallBackSignaler(5000);
    }

    //Problems with wating for locater thread. Never returns.
//    @SmallTest
//    public void testEnterNameForCurrentLocation() {
//        launchDialogAndTestShowing(true);
//
//        assertEquals(0, getData(mAdapter).size());
//
//        GvData datum = enterRandomNameForCurrent();
//
//        pressDialogButton(DialogButton.DONE);
//
//        GvDataList data = getData(mAdapter);
//
//        assertEquals(1, data.size());
//        assertEquals(datum, data.getItem(0));
//    }
//
    @Override
    public void onLocated(Location location) {
        mCurrent = new LatLng(location.getLatitude(), location.getLongitude());
        mSignaler.signal();
        mSignaler.reset();
    }

    @Override
    public void onLocationClientConnected(Location location) {
        onLocated(location);
    }
//
//    private GvData enterRandomNameForCurrent() {
//        assertTrue(isDataNulled());
//        getCurrentLocation();
//        mSignaler.waitForSignal();
//        GvLocationList.GvLocation data =
//                new GvLocationList.GvLocation(mCurrent, RandomString.nextWord());
//        enterData(data);
//        assertTrue(isDataEntered());
//        mSolo.waitForText(data.getName());
//        mSolo.clickInList(1);
//        mSolo.sleep(3000);
//
//        return data;
//    }
//
//    private void getCurrentLocation() {
//        mSignaler.reset();
//        mActivity.runOnUiThread(new Runnable() {
//            public void run() {
//                mLocater.locate();
//            }
//        });
//    }
}
