/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.EditScreenReviewData;
import com.chdryra.android.reviewer.test.TestUtils.DialogAddListener;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.chdryra.android.testutils.CallBackSignaler;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataAddTest<T extends GvData> extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int REQUEST_CODE = 1976;
    private static final String DIALOG_TAG = "TestAddDialog";
    private final Class<? extends DialogGvDataAdd> mDialogClass;
    protected Solo mSolo;
    protected DialogGvDataAdd mDialog;
    protected DialogAddListener<T> mListener;
    protected ReviewViewAdapter mAdapter;
    protected Activity mActivity;
    protected CallBackSignaler mSignaler;

    protected enum DialogButton {CANCEL, ADD, DONE}

    protected DialogGvDataAddTest(Class<? extends DialogGvDataAdd<T>> dialogClass) {
        super(ActivityReviewView.class);
        mDialogClass = dialogClass;
        mSignaler = new CallBackSignaler(30);
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing(false);

        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());
        enterDataAndTest();
        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());

        pressDialogButton(DialogButton.CANCEL);

        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());
        assertFalse(mDialog.isVisible());

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

        assertEquals(0, getData(mAdapter).size());

        GvData datum1 = testQuickSet(true);
        GvData datum2 = testQuickSet(true);
        GvData datum3 = testQuickSet(false);

        pressDialogButton(DialogButton.DONE);

        GvDataList data = getData(mAdapter);

        if (datum1.getGvDataType().equals(GvCommentList.GvComment.TYPE)) {
            ((GvCommentList.GvComment) datum1).setIsHeadline(true);
        }
        assertEquals(3, data.size());
        assertTrue(data.contains(datum1));
        assertTrue(data.contains(datum2));
        assertTrue(data.contains(datum3));
    }

    //protected methods
    protected boolean isDataEntered() {
        return mSolo.getEditText(0).getText().toString().length() > 0;
    }

    protected boolean isDataNulled() {
        return !isDataEntered();
    }

    protected void enterData(GvData datum) {
        SoloDataEntry.enter(mSolo, datum);
    }

    protected void launchDialogAndTestShowing(boolean quickSet) {
        Bundle args = new Bundle();
        args.putBoolean(DialogGvDataAdd.QUICK_SET, quickSet);

        assertFalse(mDialog.isShowing());

        LauncherUi.launch(mDialog, mListener, REQUEST_CODE, DIALOG_TAG, args);

        final FragmentManager manager = mActivity.getFragmentManager();
        mSignaler.reset();
        mActivity.runOnUiThread(new Runnable() {
            //Overridden
            public void run() {
                manager.executePendingTransactions();
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();
        assertTrue(mDialog.isShowing());
        assertTrue(mSolo.waitForDialogToOpen());
    }

    protected GvDataList getData(final ReviewViewAdapter adapter) {
        return adapter.getGridData();
    }

    protected GvData enterDataAndTest() {
        assertTrue(isDataNulled());
        GvData data = GvDataMocker.getDatum(mDialog.getGvDataType());
        enterData(data);
        assertTrue(isDataEntered());

        return data;
    }

    protected GvData testQuickSet(boolean pressAdd) {
        mListener.reset();
        mSignaler.reset();

        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());
        GvData data = enterDataAndTest();
        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());

        if (pressAdd) pressDialogButton(DialogButton.ADD);

        return data;
    }

    protected void pressDialogButton(final DialogButton button) {
        mSignaler.reset();
        mActivity.runOnUiThread(new Runnable() {
//Overridden
            public void run() {
                if (button == DialogButton.CANCEL) {
                    mDialog.clickCancelButton();
                } else if (button == DialogButton.ADD) {
                    mDialog.clickAddButton();
                } else if (button == DialogButton.DONE) {
                    mDialog.clickDoneButton();
                }
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();
    }

    private void testNotQuickSet(final boolean addButton) {
        launchDialogAndTestShowing(false);

        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());
        assertEquals(0, getData(mAdapter).size());
        final GvData datum = enterDataAndTest();
        assertNull(mListener.getAddData());
        assertNull(mListener.getDoneData());

        GvData fromDialog;
        if (addButton) {
            pressDialogButton(DialogButton.ADD);
            fromDialog = mListener.getAddData();
            assertNotNull(fromDialog);
            assertNull(mListener.getDoneData());
        } else {
            pressDialogButton(DialogButton.DONE);
            fromDialog = mListener.getDoneData();
            assertNotNull(fromDialog);
            assertEquals(mListener.getAddData(), fromDialog);
        }

        assertEquals(datum, fromDialog);

        assertEquals(0, getData(mAdapter).size());
        if (addButton) {
            assertTrue(mDialog.isShowing());
            assertTrue(isDataNulled());
        } else {
            assertFalse(mDialog.isShowing());
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mDialog = mDialogClass.newInstance();
        mListener = new DialogAddListener<>();

        mAdapter = Administrator.get(getInstrumentation().getTargetContext())
                .newReviewBuilder().getDataBuilder(mDialog.getGvDataType());

        Intent i = new Intent();
        Context context = getInstrumentation().getTargetContext();
        Administrator admin = Administrator.get(context);
        admin.packView(EditScreenReviewData.newScreen(context, mDialog.getGvDataType()), i);
        setActivityIntent(i);
        mActivity = getActivity();

        FragmentManager manager = mActivity.getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mListener, DIALOG_TAG);
        ft.commit();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }
}
