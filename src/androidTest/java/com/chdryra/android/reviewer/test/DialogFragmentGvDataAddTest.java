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
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment;
import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.GvAdapter;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.LaunchableUi;
import com.chdryra.android.reviewer.LauncherUi;
import com.chdryra.android.reviewer.test.TestUtils.DialogAddListener;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogFragmentGvDataAddTest<T extends GvDataList.GvData> extends
        ActivityInstrumentationTestCase2<ActivityFeed> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestAddDialog";
    protected Solo                                     mSolo;
    protected DialogFragmentGvDataAdd                  mDialog;
    protected DialogAddListener<T>                     mListener;
    protected GvAdapter                                mAdapter;
    protected Activity                                 mActivity;
    private   Class<? extends DialogFragmentGvDataAdd> mDialogClass;

    protected abstract T enterData();

    protected abstract boolean isDataEntered();

    protected abstract boolean isDataNulled();

    protected DialogFragmentGvDataAddTest(Class<? extends DialogFragmentGvDataAdd> dialogClass) {
        super(ActivityFeed.class);
        mDialogClass = dialogClass;
    }

    protected void testCancelButton() {
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

    protected void testAddButtonNotQuickSet() {
        testNotQuickSet(true);
    }

    protected void testDoneButtonNotQuickSet() {
        testNotQuickSet(false);
    }

    protected void testQuickSet() {
        launchDialogAndTestShowing(true);

        final GvAdapter controller = mAdapter;
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

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mDialog = mDialogClass.newInstance();
        mListener = new DialogAddListener<>();

        mActivity = getActivity();
        FragmentManager manager = mActivity.getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mListener, DIALOG_TAG);
        ft.commit();

        mAdapter = Administrator.get(getInstrumentation().getTargetContext())
                .createNewReviewInProgress();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    protected void launchDialogAndTestShowing(boolean quickSet) {
        Bundle args = getControllerBundle();
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, quickSet);

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

    protected GvDataList getData(final GvAdapter adapter) {
        return adapter.getData(mDialog.getGvType());
    }

    protected Bundle getControllerBundle() {
        return Administrator.get(getInstrumentation().getTargetContext()).pack(mAdapter);
    }

    private GvDataList.GvData enterDataAndTest() {
        assertTrue(isDataNulled());
        GvDataList.GvData data = enterData();
        assertTrue(isDataEntered());

        return data;
    }

    private void testNotQuickSet(final boolean addButton) {
        launchDialogAndTestShowing(false);

        final DialogAddListener<T> listener = mListener;
        final GvAdapter adap = mAdapter;
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
