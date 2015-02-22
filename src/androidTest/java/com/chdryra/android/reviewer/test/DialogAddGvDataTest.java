/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment;
import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.DialogAddGvData;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.LaunchableUi;
import com.chdryra.android.reviewer.LauncherUi;
import com.chdryra.android.reviewer.ReviewViewAdapter;
import com.chdryra.android.reviewer.test.TestUtils.DialogAddListener;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogAddGvDataTest<T extends GvDataList.GvData> extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestAddDialog";
    protected Solo                             mSolo;
    protected DialogAddGvData                  mDialog;
    protected DialogAddListener<T>             mListener;
    protected ReviewViewAdapter                mAdapter;
    protected Activity                         mActivity;
    private   Class<? extends DialogAddGvData> mDialogClass;

    protected DialogAddGvDataTest(Class<? extends DialogAddGvData<T>> dialogClass) {
        super(ActivityReviewView.class);
        mDialogClass = dialogClass;
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing(false);

        final DialogAddListener<T> listener = mListener;

        assertNull(listener.getData());
        enterDataAndTest();
        assertNull(listener.getData());

        final DialogCancelAddDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickCancelButton();
                assertNull(listener.getData());
                assertFalse(dialog.isVisible());
            }
        });
    }

    @SmallTest
    public void testAddButtonNotQuickSet() {
        testNotQuickSet(true);
    }

    @SmallTest
    public void testDoneButtonNotQuickSet() {
        testNotQuickSet(false);
    }

    @SmallTest
    public void testQuickSet() {
        launchDialogAndTestShowing(true);

        final ReviewViewAdapter controller = mAdapter;
        assertEquals(0, getData(controller).size());

        final GvDataList.GvData datum1 = testQuickSet(true);
        final GvDataList.GvData datum2 = testQuickSet(true);
        final GvDataList.GvData datum3 = testQuickSet(false);

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

    protected boolean isDataEntered() {
        return mSolo.getEditText(0).getText().toString().length() > 0;
    }

    protected boolean isDataNulled() {
        return !isDataEntered();
    }

    protected void enterData(GvDataList.GvData datum) {
        SoloDataEntry.enter(mSolo, datum);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mDialog = mDialogClass.newInstance();
        mListener = new DialogAddListener<>();

        mAdapter = Administrator.get(getInstrumentation().getTargetContext())
                .newReviewBuilder().getDataBuilder(mDialog.getGvType());

        Intent i = new Intent();
        ActivityReviewView.packParameters(mDialog.getGvType(), false, i);
        setActivityIntent(i);

        mActivity = getActivity();
        FragmentManager manager = mActivity.getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mListener, DIALOG_TAG);
        ft.commit();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    protected void launchDialogAndTestShowing(boolean quickSet) {
        Bundle args = new Bundle();
        args.putBoolean(DialogAddGvData.QUICK_SET, quickSet);

        final DialogAddListener<T> listener = mListener;
        final DialogTwoButtonFragment dialog = mDialog;
        final FragmentManager manager = mActivity.getFragmentManager();

        assertFalse(dialog.isShowing());

        LauncherUi.launch((LaunchableUi) dialog, listener, REQUEST_CODE, DIALOG_TAG, args);

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                manager.executePendingTransactions();
                assertTrue(dialog.isShowing());
            }
        });

        assertTrue(mSolo.waitForDialogToOpen());
    }

    protected GvDataList getData(final ReviewViewAdapter adapter) {
        return adapter.getGridData();
    }

    private GvDataList.GvData enterDataAndTest() {
        assertTrue(isDataNulled());
        GvDataList.GvData data = GvDataMocker.getDatum(mDialog.getGvType());
        enterData(data);
        assertTrue(isDataEntered());

        return data;
    }

    private void testNotQuickSet(final boolean addButton) {
        launchDialogAndTestShowing(false);

        final DialogAddListener<T> listener = mListener;
        final ReviewViewAdapter adap = mAdapter;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getData());
        assertEquals(0, getData(adap).size());
        final GvDataList.GvData datum = enterDataAndTest();
        assertNull(listener.getData());

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (addButton) {
                    dialog.clickAddButton();
                } else {
                    dialog.clickDoneButton();
                }

                assertNotNull(listener.getData());
                assertEquals(datum, listener.getData());

                assertEquals(0, getData(adap).size());
                if (addButton) {
                    assertTrue(dialog.isShowing());
                    assertTrue(isDataNulled());
                } else {
                    assertFalse(dialog.isShowing());
                }
            }
        });
    }

    private GvDataList.GvData testQuickSet(boolean pressAdd) {
        final DialogAddListener<T> listener = mListener;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getData());
        GvDataList.GvData data = enterDataAndTest();
        assertNull(listener.getData());

        if (pressAdd) {
            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.clickAddButton();
                }
            });
        }

        return data;
    }
}
