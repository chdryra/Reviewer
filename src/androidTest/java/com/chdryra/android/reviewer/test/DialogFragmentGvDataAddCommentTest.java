/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
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
import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.LaunchableUI;
import com.chdryra.android.reviewer.LauncherUI;
import com.chdryra.android.reviewer.test.TestUtils.DialogAddListener;
import com.chdryra.android.testutils.RandomStringGenerator;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataAddCommentTest extends
        ActivityInstrumentationTestCase2<ActivityFeed> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestAddDialog";

    private Solo                                       mSolo;
    private ConfigGvDataAddEditDisplay.AddComment      mDialog;
    private DialogAddListener<GvCommentList.GvComment> mListener;
    private ControllerReview                           mController;
    private Activity                                   mActivity;

    public DialogFragmentGvDataAddCommentTest() {
        super(ActivityFeed.class);
    }

    @SmallTest
    public void testLaunches() {
        launchDialogAndTestShowing(false);
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing(false);

        String tag = RandomStringGenerator.nextWord();
        getInstrumentation().sendStringSync(tag);
        assertNull(mListener.getData());

        final DialogCancelAddDoneFragment dialog = mDialog;
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickCancelButton();
            }
        });

        assertNull(mListener.getData());
        assertFalse(mDialog.isVisible());
    }

    @SmallTest
    public void testAddButtonNotQuickSet() {
        launchDialogAndTestShowing(false);

        final String tag = RandomStringGenerator.nextSentence();
        final Solo solo = mSolo;
        final DialogAddListener<GvCommentList.GvComment> listener = mListener;
        final ControllerReview controller = mController;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertFalse(solo.searchEditText(tag));
        assertEquals(0, getControllerData(controller).size());

        getInstrumentation().sendStringSync(tag);
        assertTrue(solo.searchEditText(tag));
        assertNull(listener.getData());

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickAddButton();
                assertNotNull(listener.getData());
                assertEquals(tag, listener.getData().getComment());

                assertEquals(0, getControllerData(controller).size());
                assertTrue(dialogIsShowing(dialog));
                assertTrue(solo.getEditText(0).getText().toString().length() == 0);
            }
        });
    }

    @SmallTest
    public void testDoneButtonNotQuickSet() {
        launchDialogAndTestShowing(false);

        final String tag = RandomStringGenerator.nextWord();
        final Solo solo = mSolo;
        final DialogAddListener<GvCommentList.GvComment> listener = mListener;
        final ControllerReview controller = mController;
        final DialogCancelAddDoneFragment dialog = mDialog;

        assertFalse(solo.searchEditText(tag));
        assertEquals(0, getControllerData(controller).size());

        solo.typeText(solo.getEditText(0), tag);
        assertTrue(solo.searchEditText(tag));
        assertNull(listener.getData());

        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                dialog.clickDoneButton();

                assertNotNull(listener.getData());
                assertEquals(tag, listener.getData().getComment());
                assertEquals(0, getControllerData(controller).size());
                assertFalse(dialogIsShowing(dialog));
            }
        });
    }

    @SmallTest
    public void testQuickSet() {
        launchDialogAndTestShowing(true);

        final Solo solo = mSolo;
        final ControllerReview controller = mController;
        assertEquals(0, getControllerData(controller).size());
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                String tag1 = RandomStringGenerator.nextWord();
                solo.typeText(solo.getEditText(0), tag1);
                solo.clickOnButton("Add");

                String tag2 = RandomStringGenerator.nextWord();
                solo.typeText(solo.getEditText(0), tag2);
                solo.clickOnButton("Add");

                String tag3 = RandomStringGenerator.nextWord();
                solo.typeText(solo.getEditText(0), tag3);
                solo.clickOnButton("Done");

                GvCommentList data = (GvCommentList) getControllerData(controller);
                assertEquals(3, data.size());
                assertEquals(tag1, data.getItem(0).getComment());
                assertEquals(tag2, data.getItem(1).getComment());
                assertEquals(tag3, data.getItem(2).getComment());
            }
        });
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDialog = new ConfigGvDataAddEditDisplay.AddComment();
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

    private void launchDialogAndTestShowing(boolean quickSet) {
        Bundle args = getControllerBundle();
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, quickSet);

        final DialogAddListener<GvCommentList.GvComment> listener = mListener;
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

    private boolean dialogIsShowing(final DialogFragment dialog) {
        return dialog.getDialog() != null && dialog.getDialog().isShowing();
    }

    private GvDataList getControllerData(final ControllerReview controller) {
        return controller.getData(GvDataList.GvType.COMMENTS);
    }

    private Bundle getControllerBundle() {
        return Administrator.get(getInstrumentation().getTargetContext()).pack(mController);
    }
}

