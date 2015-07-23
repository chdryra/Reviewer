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

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.EditScreen;
import com.chdryra.android.reviewer.test.TestUtils.DialogAddListener;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataAddTest<T extends GvData> extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestAddDialog";
    private final Class<? extends DialogGvDataAdd> mDialogClass;
    protected     Solo                             mSolo;
    protected     DialogGvDataAdd                  mDialog;
    protected     DialogAddListener<T>             mListener;
    protected     ReviewViewAdapter                mAdapter;
    protected     Activity                         mActivity;

    protected DialogGvDataAddTest(Class<? extends DialogGvDataAdd<T>> dialogClass) {
        super(ActivityReviewView.class);
        mDialogClass = dialogClass;
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing(false);

        final DialogAddListener<T> listener = mListener;

        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());
        enterDataAndTest();
        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());

        final DialogCancelAddDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickCancelButton();
                assertNull(listener.getAddData());
                assertNull(listener.getDoneData());
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

        final GvData datum0 = testQuickSet(true);
        final GvData datum2 = testQuickSet(true);
        final GvData datum3 = testQuickSet(false);

        final DialogCancelAddDoneFragment dialog = mDialog;

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickDoneButton();

                GvDataList data = getData(controller);

                GvData datum1 = datum0;
                if (datum0.getGvDataType().equals(GvCommentList.GvComment.TYPE)) {
                    GvCommentList.GvComment comment = (GvCommentList.GvComment) datum0;
                    comment.setIsHeadline(true);
                    datum1 = comment;
                }
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

    protected void enterData(GvData datum) {
        SoloDataEntry.enter(mSolo, datum);
    }

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
        admin.packView(EditScreen.newScreen(context, mDialog.getGvDataType()), i);
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
        args.putBoolean(DialogGvDataAdd.QUICK_SET, quickSet);

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

    protected GvData enterDataAndTest() {
        assertTrue(isDataNulled());
        GvData data = GvDataMocker.getDatum(mDialog.getGvDataType());
        enterData(data);
        assertTrue(isDataEntered());

        return data;
    }

    protected GvData testQuickSet(boolean pressAdd) {
        mListener.reset();
        final DialogAddListener<T> listener = mListener;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());
        GvData data = enterDataAndTest();
        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());

        if (pressAdd) {
            mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.clickAddButton();
                }
            });
        }

        return data;
    }

    private void testNotQuickSet(final boolean addButton) {
        launchDialogAndTestShowing(false);

        final DialogAddListener<T> listener = mListener;
        final ReviewViewAdapter adapter = mAdapter;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());
        assertEquals(0, getData(adapter).size());
        final GvData datum = enterDataAndTest();
        assertNull(listener.getAddData());
        assertNull(listener.getDoneData());

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                GvData fromDialog;
                if (addButton) {
                    dialog.clickAddButton();
                    fromDialog = listener.getAddData();
                    assertNotNull(fromDialog);
                    assertNull(listener.getDoneData());
                } else {
                    dialog.clickDoneButton();
                    fromDialog = listener.getDoneData();
                    assertNotNull(fromDialog);
                    assertEquals(listener.getAddData(), fromDialog);
                }

                assertEquals(datum, fromDialog);

                assertEquals(0, getData(adapter).size());
                if (addButton) {
                    assertTrue(dialog.isShowing());
                    assertTrue(isDataNulled());
                } else {
                    assertFalse(dialog.isShowing());
                }
            }
        });
    }
}
