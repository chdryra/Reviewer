/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 March, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.location.Location;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.DialogAddListener;
import com.chdryra.android.testutils.CallBackSignaler;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddLocationTest extends DialogAddGvDataTest<GvLocationList.GvLocation>
        implements LocationClientConnector.Locatable {
    private static final GvLocationList.GvLocation TAYYABS    =
            new GvLocationList.GvLocation(new LatLng(51.517264, -0.063484), "Tayyabs");
    private static final String                    TAYYABSADD = "Fieldgate Street, London, " +
            "United Kingdom";

    private static final GvLocationList.GvLocation TOWERBRIDGE    =
            new GvLocationList.GvLocation(new LatLng(51.50418459999999, -0.07632209999999999),
                    "Tower Bridge");
    private static final String                    TOWERBRIDGEADD = "Tower Bridge, London, " +
            "United Kingdom";

    private static final GvLocationList.GvLocation   DISHOOM    =
            new GvLocationList.GvLocation(new LatLng(51.51243, -0.126909), "Dishoom");
    private static final GvLocationList.GvLocation[] LOCS       = {TAYYABS, TOWERBRIDGE, DISHOOM};
    private static final String                      DISHOOMADD = "Upper St Martin's Lane, " +
            "London, United Kingdom";
    private static final String[]                    LOCSADD    = {TAYYABSADD, TOWERBRIDGEADD,
            DISHOOMADD};

    private LatLng                  mCurrent;
    private CallBackSignaler        mSignaler;
    private LocationClientConnector mLocater;

    public DialogAddLocationTest() {
        super(ConfigGvDataAddEditView.AddLocation.class);
    }

    @Override
    public void testQuickSet() {
        launchDialogAndTestShowing(true);

        final ReviewViewAdapter controller = mAdapter;
        assertEquals(0, getData(controller).size());

        final GvData datum1 = testQuickSet(true, 0);
        final GvData datum2 = testQuickSet(true, 1);
        final GvData datum3 = testQuickSet(false, 2);

        final DialogCancelAddDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickDoneButton();

                GvDataList data = getData(controller);

                assertEquals(3, data.size());
                assertEquals(datum1, data.getItem(0));
                assertEquals(datum2, data.getItem(1));
                assertEquals(datum3, data.getItem(2));
            }
        });
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLocater = new LocationClientConnector(getActivity(), this);
        mSignaler = new CallBackSignaler(5000);
    }

    protected GvData enterDataAndTest() {
        return enterDataAndTest(0);
    }

    //Problems with wating for locater thread. Never returns.
//    @SmallTest
//    public void testEnterNameForCurrentLocation() {
//        launchDialogAndTestShowing(true);
//
//        final ReviewViewAdapter controller = mAdapter;
//        assertEquals(0, getData(controller).size());
//
//        final GvDataList.GvData datum = enterRandomNameForCurrent();
//
//        final DialogCancelAddDoneFragment dialog = mDialog;
//        mActivity.runOnUiThread(new Runnable() {
//            public void run() {
//                dialog.clickDoneButton();
//
//                GvDataList data = getData(controller);
//
//                assertEquals(1, data.size());
//                assertEquals(datum, data.getItem(0));
//            }
//        });
//    }

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
        final DialogAddListener<GvLocationList.GvLocation> listener = mListener;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());
        GvData data = enterDataAndTest(index);
        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());

        if (pressAdd) {
            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.clickAddButton();
                }
            });
        }

        assertFalse(mSignaler.timedOut());
        return data;
    }

    private void getCurrentLocation() {
        mSignaler.reset();
        mLocater.locate();
    }
}
