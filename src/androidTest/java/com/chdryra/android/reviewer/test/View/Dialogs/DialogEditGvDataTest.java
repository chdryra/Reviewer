/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityFeed;
import com.chdryra.android.reviewer.View.Dialogs.DialogEditGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.test.TestUtils.DialogEditListener;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogEditGvDataTest<T extends GvData> extends
        ActivityInstrumentationTestCase2<ActivityFeed> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestEditDialog";
    private final Class<? extends DialogEditGvData> mDialogClass;
    protected     Solo                              mSolo;
    protected     DialogEditGvData                  mDialog;
    protected     DialogEditListener<T>             mListener;
    protected     Activity                          mActivity;

    protected abstract GvData getDataShown();

    protected DialogEditGvDataTest(Class<? extends DialogEditGvData> dialogClass) {
        super(ActivityFeed.class);
        mDialogClass = dialogClass;
    }

    @SmallTest
    public void testCancelButton() {
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

    @SmallTest
    public void testDoneButton() {
        final GvData datumOld = launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;
        final DialogCancelDeleteDoneFragment dialog = mDialog;

        assertNull(listener.getDataNew());
        assertNull(listener.getDataOld());

        final GvData datumNew = editDataAndTest();

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

    @SmallTest
    public void testDeleteButtonNoEditCancel() {
        testDeleteButtonNoEdit(false, false);
    }

    @SmallTest
    public void testDeleteButtonNoEditConfirm() {
        testDeleteButtonNoEdit(true, false);
    }

    @SmallTest
    public void testDeleteButtonWithEditCancel() {
        testDeleteButtonNoEdit(false, true);
    }

    @SmallTest
    public void testDeleteButtonWithEditConfirm() {
        testDeleteButtonNoEdit(true, true);
    }

    protected void enterData(GvData datum) {
        SoloDataEntry.enter(mSolo, datum);
    }

    protected void testDeleteButtonNoEdit(final boolean confirmDelete, final boolean doEdit) {
        final GvData datumOld = launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;
        final DialogCancelDeleteDoneFragment dialog = mDialog;
        final Solo solo = mSolo;
        final String deleteConfirmTag = DialogDeleteConfirm.DELETE_CONFIRM_TAG;

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

        final DialogAlertFragment dialogConfirm = (DialogAlertFragment) mActivity
                .getFragmentManager().findFragmentByTag(deleteConfirmTag);
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (confirmDelete) {
                    dialogConfirm.clickPositiveButton();
                    assertFalse(dialogConfirm.isShowing());
                    assertFalse(dialog.isShowing());
                    assertNotNull(listener.getDataOld());
                    assertNull(listener.getDataNew());
                    assertEquals(datumOld, listener.getDataOld());
                } else {
                    dialogConfirm.clickNegativeButton();
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

    protected GvData newDatum() {
        return GvDataMocker.getDatum(mDialog.getGvDataType());
    }

    protected GvData launchDialogAndTestShowing() {
        final GvData datum = newDatum();
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, datum, args);

        assertFalse(mDialog.isShowing());

        LauncherUi.launch(mDialog, mListener, REQUEST_CODE, DIALOG_TAG, args);

        final FragmentManager manager = mActivity.getFragmentManager();
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                manager.executePendingTransactions();

            }
        });
        mSolo.waitForDialogToOpen();
        assertTrue(mDialog.isShowing());
        assertEquals(datum, getDataShown());

        return datum;
    }

    protected GvData getNewDatum() {
        return GvDataMocker.getDatum(mDialog.getGvDataType());
    }

    private boolean deleteConfirmIsShowing() {
        final Fragment f = mActivity.getFragmentManager()
                .findFragmentByTag(DialogDeleteConfirm.DELETE_CONFIRM_TAG);
        final DialogAlertFragment dialogConfirm = (DialogAlertFragment) f;

        return dialogConfirm != null && dialogConfirm.isShowing();
    }

    private GvData editDataAndTest() {
        GvData currentDatum = getDataShown();
        GvData newDatum = getNewDatum();

        enterData(newDatum);

        assertFalse(currentDatum.equals(newDatum));
        assertEquals(newDatum, getDataShown());

        return newDatum;
    }
}

