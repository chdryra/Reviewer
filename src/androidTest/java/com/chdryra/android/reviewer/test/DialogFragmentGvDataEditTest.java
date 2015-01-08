/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirmFragment;
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment;
import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.DialogFragmentGvDataEdit;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvDataPacker;
import com.chdryra.android.reviewer.LaunchableIU2;
import com.chdryra.android.reviewer.LauncherIU2;
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

    protected void testCancelButton() {
        launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;

        assertNull(listener.getDataOld());
        assertNull(listener.getDataNew());

        editDataAndTest();

        final DialogCancelDeleteDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickCancelButton();

                assertNull(listener.getDataOld());
                assertNull(listener.getDataNew());
                assertFalse(dialog.isShowing());
            }
        });
    }

    protected void testDoneButton() {
        final T datumOld = launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;
        final DialogCancelDeleteDoneFragment dialog = mDialog;

        assertNull(listener.getDataNew());
        assertNull(listener.getDataOld());

        final T datumNew = editDataAndTest();

        assertNull(listener.getDataNew());
        assertNull(listener.getDataOld());

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickDoneButton();

                assertNotNull(listener.getDataOld());
                assertNotNull(listener.getDataNew());
                assertEquals(datumOld, listener.getDataOld());
                assertEquals(datumNew, listener.getDataNew());
                assertFalse(dialog.isShowing());
            }
        });
    }

    protected void testDeleteButtonNoEditCancel() {
        testDeleteButtonNoEdit(false, false);
    }

    protected void testDeleteButtonNoEditConfirm() {
        testDeleteButtonNoEdit(true, false);
    }

    protected void testDeleteButtonWithEditCancel() {
        testDeleteButtonNoEdit(false, true);
    }

    protected void testDeleteButtonWithEditConfirm() {
        testDeleteButtonNoEdit(true, true);
    }

    protected void testDeleteButtonNoEdit(final boolean confirmDelete, final boolean doEdit) {
        final T datumOld = launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;
        final DialogCancelDeleteDoneFragment dialog = mDialog;
        final Solo solo = mSolo;
        final String deleteConfirmTag = DialogDeleteConfirmFragment.DELETE_CONFIRM_TAG;

        if (doEdit) editDataAndTest();

        assertFalse(deleteConfirmIsShowing());
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickDeleteButton();
            }
        });
        solo.waitForFragmentByTag(deleteConfirmTag);
        assertTrue(deleteConfirmIsShowing());

        assertNull(listener.getDataOld());
        assertNull(listener.getDataNew());

        final DialogDeleteConfirmFragment dialogConfirm = (DialogDeleteConfirmFragment) mActivity
                .getFragmentManager().findFragmentByTag(deleteConfirmTag);
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (confirmDelete) {
                    dialogConfirm.clickConfirmButton();
                    assertFalse(dialogConfirm.isShowing());
                    assertFalse(dialog.isShowing());
                    assertNotNull(listener.getDataOld());
                    assertNull(listener.getDataNew());
                    assertEquals(datumOld, listener.getDataOld());
                } else {
                    dialogConfirm.clickCancelButton();
                    assertFalse(dialogConfirm.isShowing());
                    assertTrue(dialog.isShowing());
                    assertNull(listener.getDataOld());
                    assertNull(listener.getDataNew());
                }
            }
        });
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
        final DialogTwoButtonFragment dialog = mDialog;
        final FragmentManager manager = mActivity.getFragmentManager();

        assertFalse(dialog.isShowing());

        LauncherIU2.launch((LaunchableIU2) dialog, listener, REQUEST_CODE, DIALOG_TAG, args);

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                manager.executePendingTransactions();

            }
        });

        mSolo.waitForDialogToOpen();

        assertTrue(dialog.isShowing());
        if (mDialog.getGvType() != GvDataList.GvType.IMAGES) assertEquals(datum, getDataShown());

        return (T) datum;
    }

    private boolean deleteConfirmIsShowing() {
        final Fragment f = mActivity.getFragmentManager()
                .findFragmentByTag(DialogDeleteConfirmFragment.DELETE_CONFIRM_TAG);
        final DialogDeleteConfirmFragment dialogConfirm = (DialogDeleteConfirmFragment) f;

        return dialogConfirm != null && dialogConfirm.isShowing();
    }

    private T editDataAndTest() {
        T currentDatum = getDataShown();
        T newDatum = editData();
        assertFalse(currentDatum.equals(newDatum));
        assertEquals(newDatum, getDataShown());

        return newDatum;
    }
}

