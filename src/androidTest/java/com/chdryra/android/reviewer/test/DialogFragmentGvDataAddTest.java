/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.LaunchableUI;
import com.chdryra.android.reviewer.LauncherUI;
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
    protected ControllerReview                         mController;
    protected Activity                                 mActivity;
    private   Class<? extends DialogFragmentGvDataAdd> mDialogClass;

    protected abstract GvDataList.GvData enterData();

    protected abstract boolean isDataEntered();

    protected abstract boolean isDataNulled();

    protected DialogFragmentGvDataAddTest(Class<? extends DialogFragmentGvDataAdd> dialogClass) {
        super(ActivityFeed.class);
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

        final ControllerReview controller = mController;
        assertEquals(0, getControllerData(controller).size());

        final GvDataList.GvData datum1 = testQuickSet(true);
        final GvDataList.GvData datum2 = testQuickSet(true);
        final GvDataList.GvData datum3 = testQuickSet(true);

        final DialogCancelAddDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickDoneButton();

                GvDataList data = getControllerData(controller);

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
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mListener, DIALOG_TAG);
        ft.commit();

        mController = Administrator.get(getInstrumentation().getTargetContext())
                .createNewReviewInProgress();

        mActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    protected void launchDialogAndTestShowing(boolean quickSet) {
        Bundle args = getControllerBundle();
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, quickSet);

        final DialogAddListener<T> listener = mListener;
        final DialogFragment dialog = mDialog;
        final FragmentManager manager = mActivity.getFragmentManager();

        assertFalse(dialogIsShowing(dialog));

        LauncherUI.launch((LaunchableUI) dialog, listener, REQUEST_CODE, DIALOG_TAG, args);

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                manager.executePendingTransactions();
                assertTrue(dialogIsShowing(dialog));
            }
        });
    }

    protected boolean dialogIsShowing(final DialogFragment dialog) {
        return dialog.getDialog() != null && dialog.getDialog().isShowing();
    }

    protected GvDataList getControllerData(final ControllerReview controller) {
        return controller.getData(mDialog.getGvType());
    }

    protected Bundle getControllerBundle() {
        return Administrator.get(getInstrumentation().getTargetContext()).pack(mController);
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
        final ControllerReview controller = mController;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getData());
        assertEquals(0, getControllerData(controller).size());
        final GvDataList.GvData comment = enterDataAndTest();
        assertNull(listener.getData());

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (addButton) {
                    dialog.clickAddButton();
                } else {
                    dialog.clickDoneButton();
                }

                assertNotNull(listener.getData());
                assertEquals(comment, listener.getData());

                assertEquals(0, getControllerData(controller).size());
                if (addButton) {
                    assertTrue(dialogIsShowing(dialog));
                    assertTrue(isDataNulled());
                } else {
                    assertFalse(dialogIsShowing(dialog));
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
