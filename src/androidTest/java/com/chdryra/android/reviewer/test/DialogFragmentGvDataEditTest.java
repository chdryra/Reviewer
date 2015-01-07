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

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.DialogFragmentGvDataEdit;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvDataPacker;
import com.chdryra.android.reviewer.LaunchableUI;
import com.chdryra.android.reviewer.LauncherUI;
import com.chdryra.android.reviewer.test.TestUtils.DialogEditListener;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogFragmentGvDataEditTest<T extends GvDataList.GvData> extends
        ActivityInstrumentationTestCase2<ActivityFeed> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestEditDialog";

    protected Solo                                      mSolo;
    protected DialogFragmentGvDataEdit                  mDialog;
    protected DialogEditListener<T>                     mListener;
    protected Activity                                  mActivity;
    private   Class<? extends DialogFragmentGvDataEdit> mDialogClass;

    protected abstract T editData();

    protected abstract T getDataShown();

    protected DialogFragmentGvDataEditTest(Class<? extends DialogFragmentGvDataEdit> dialogClass) {
        super(ActivityFeed.class);
        mDialogClass = dialogClass;
    }

    @SmallTest
    public void testCancelButtonNoEdit() {
        testCancelButton(false);
    }

    @SmallTest
    public void testCancelButtonWithEdit() {
        testCancelButton(true);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mDialog = mDialogClass.newInstance();

        mListener = new DialogEditListener<>();
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mListener, DIALOG_TAG);
        ft.commit();

        mActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    protected T launchDialogAndTestShowing() {
        final GvDataList.GvData datum = GvDataMocker.getDatum(mDialog.getGvType());
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, datum, args);

        final DialogEditListener<T> listener = mListener;
        final DialogFragment dialog = mDialog;
        final FragmentManager manager = mActivity.getFragmentManager();

        assertFalse(dialogIsShowing(dialog));

        LauncherUI.launch((LaunchableUI) dialog, listener, REQUEST_CODE, DIALOG_TAG, args);

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                manager.executePendingTransactions();

            }
        });

        mSolo.waitForDialogToOpen();

        assertTrue(dialogIsShowing(dialog));
        assertEquals(datum, getDataShown());

        return (T) datum;
    }

    protected boolean dialogIsShowing(final DialogFragment dialog) {
        return dialog.getDialog() != null && dialog.getDialog().isShowing();
    }

    private void testCancelButton(boolean withEdit) {
        launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;

        assertNull(listener.getDataOld());
        assertNull(listener.getDataNew());

        if (withEdit) editDataAndTest();

        final DialogCancelDeleteDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickCancelButton();

                assertNull(listener.getDataOld());
                assertNull(listener.getDataNew());

                assertFalse(dialog.isVisible());
            }
        });
    }

    private GvDataList.GvData editDataAndTest() {
        T currentDatum = getDataShown();
        T newDatum = editData();
        assertFalse(currentDatum.equals(newDatum));
        assertEquals(newDatum, getDataShown());

        return newDatum;
    }
}

