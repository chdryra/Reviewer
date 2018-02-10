/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.startouch.test.View.Dialogs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.corelibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.corelibrary.Dialogs.DialogDeleteConfirm;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityUsersFeed;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogGvDataEdit;

import com.chdryra.android.startouch.test.TestUtils.DialogEditListener;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.SoloDataEntry;
import com.chdryra.android.testutils.CallBackSignaler;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvDataEditTest<T extends GvData> extends
        ActivityInstrumentationTestCase2<ActivityUsersFeed> {
    private static final int REQUEST_CODE = 1976;
    private static final String DIALOG_TAG = "TestEditDialog";
    private final Class<? extends DialogGvDataEdit> mDialogClass;
    protected Solo mSolo;
    protected DialogGvDataEdit mDialog;
    protected DialogEditListener<T> mListener;
    protected Activity mActivity;
    protected CallBackSignaler mSignaler;

    private enum DialogButton {CANCEL, DELETE, DONE}

    protected DialogGvDataEditTest(Class<? extends DialogGvDataEdit> dialogClass) {
        super(ActivityUsersFeed.class);
        mDialogClass = dialogClass;
        mSignaler = new CallBackSignaler(30);
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing();

        final DialogEditListener<T> listener = mListener;

        assertNull(listener.getDataOld());
        assertNull(listener.getDataNew());

        editDataAndTest();

        pressDialogButton(DialogButton.CANCEL);

        assertNull(listener.getDataOld());
        assertNull(listener.getDataNew());
        assertFalse(mDialog.isShowing());
    }

    @SmallTest
    public void testDoneButton() {
        final GvData datumOld = launchDialogAndTestShowing();

        assertNull(mListener.getDataNew());
        assertNull(mListener.getDataOld());

        GvData datumNew = editDataAndTest();

        assertNull(mListener.getDataNew());
        assertNull(mListener.getDataOld());

        pressDialogButton(DialogButton.DONE);

        assertNotNull(mListener.getDataOld());
        assertNotNull(mListener.getDataNew());
        assertEquals(datumOld, mListener.getDataOld());
        assertEquals(datumNew, mListener.getDataNew());
        assertFalse(mDialog.isShowing());
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

    //protected methods
    protected abstract GvData getDataShown();

    protected GvData getEditDatum() {
        return GvDataMocker.getDatum(mDialog.getGvDataType());
    }

    protected void enterData(GvData datum) {
        SoloDataEntry.enter(mSolo, datum);
    }

    protected void testDeleteButtonNoEdit(final boolean confirmDelete, final boolean doEdit) {
        GvData datumOld = launchDialogAndTestShowing();

        String deleteConfirmTag = DialogDeleteConfirm.DELETE_CONFIRM_TAG;

        if (doEdit) editDataAndTest();

        assertFalse(deleteConfirmIsShowing());

        pressDialogButton(DialogButton.DELETE);

        mSolo.waitForFragmentByTag(deleteConfirmTag);
        assertTrue(deleteConfirmIsShowing());

        assertNull(mListener.getDataOld());
        assertNull(mListener.getDataNew());

        mSignaler.reset();
        final DialogAlertFragment dialogConfirm = (DialogAlertFragment) mActivity
                .getFragmentManager().findFragmentByTag(deleteConfirmTag);
        mActivity.runOnUiThread(new Runnable() {
            //Overridden
            public void run() {
                if (confirmDelete) {
                    dialogConfirm.clickPositiveButton();
                } else {
                    dialogConfirm.clickNegativeButton();
                }
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();
        if (confirmDelete) {
            assertFalse(dialogConfirm.isShowing());
            assertFalse(mDialog.isShowing());
            assertNotNull(mListener.getDataOld());
            assertNull(mListener.getDataNew());
            assertEquals(datumOld, mListener.getDataOld());
        } else {
            assertFalse(dialogConfirm.isShowing());
            assertTrue(mDialog.isShowing());
            assertNull(mListener.getDataOld());
            assertNull(mListener.getDataNew());
        }
    }

    protected GvData newDatum() {
        return GvDataMocker.getDatum(mDialog.getGvDataType());
    }

    protected GvData launchDialogAndTestShowing() {
        final GvData datum = newDatum();
        Bundle args = new Bundle();
        ParcelablePacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, datum, args);

        assertFalse(mDialog.isShowing());

        LauncherUiImpl.launch(mDialog, mListener, REQUEST_CODE, DIALOG_TAG, args);

        final FragmentManager manager = mActivity.getFragmentManager();
        mActivity.runOnUiThread(new Runnable() {
//Overridden
            public void run() {
                manager.executePendingTransactions();

            }
        });
        mSolo.waitForDialogToOpen();
        assertTrue(mDialog.isShowing());
        assertEquals(datum, getDataShown());

        return datum;
    }

    private boolean deleteConfirmIsShowing() {
        final Fragment f = mActivity.getFragmentManager()
                .findFragmentByTag(DialogDeleteConfirm.DELETE_CONFIRM_TAG);
        final DialogAlertFragment dialogConfirm = (DialogAlertFragment) f;

        return dialogConfirm != null && dialogConfirm.isShowing();
    }

    private GvData editDataAndTest() {
        GvData currentDatum = getDataShown();
        GvData editDatum = getEditDatum();

        enterData(editDatum);

        assertFalse(currentDatum.equals(editDatum));
        assertEquals(editDatum, getDataShown());

        return editDatum;
    }

    private void pressDialogButton(final DialogButton button) {
        mSignaler.reset();
        mActivity.runOnUiThread(new Runnable() {
//Overridden
            public void run() {
                if (button == DialogButton.CANCEL) {
                    mDialog.clickCancelButton();
                } else if (button == DialogButton.DELETE) {
                    mDialog.clickDeleteButton();
                } else if (button == DialogButton.DONE) {
                    mDialog.clickDoneButton();
                }
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();
    }

    //Overridden
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
}

