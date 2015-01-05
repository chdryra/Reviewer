/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
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
    private DialogAddListener<GvCommentList.GvComment> mFragment;
    private ControllerReview                           mController;

    public DialogFragmentGvDataAddCommentTest() {
        super(ActivityFeed.class);
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing(false);

        String tag = RandomStringGenerator.nextWord();
        assertFalse(mSolo.searchEditText(tag));

        mSolo.enterText(mSolo.getEditText(0), tag);

        assertTrue(mSolo.searchEditText(tag));
        assertNull(mFragment.getData());

        mSolo.clickOnButton("Cancel");
        getInstrumentation().waitForIdleSync();

        assertNull(mFragment.getData());
        assertFalse(dialogIsShowing());
    }

    @SmallTest
    public void testAddButtonNotQuickSet() {
        launchDialogAndTestShowing(false);

        String tag = RandomStringGenerator.nextSentence();
        assertFalse(mSolo.searchEditText(tag));
        assertEquals(0, getControllerData().size());

        mSolo.enterText(mSolo.getEditText(0), tag);

        assertTrue(mSolo.searchEditText(tag));
        assertNull(mFragment.getData());

        mSolo.clickOnButton("Add");
        getInstrumentation().waitForIdleSync();

        assertNotNull(mFragment.getData());
        assertEquals(tag, mFragment.getData().getComment());
        assertEquals(0, getControllerData().size());
        assertTrue(dialogIsShowing());
        assertTrue(mSolo.getEditText(0).getText().toString().length() == 0);
    }

    @SmallTest
    public void testDoneButtonNotQuickSet() {
        launchDialogAndTestShowing(false);

        String tag = RandomStringGenerator.nextWord();
        assertFalse(mSolo.searchEditText(tag));
        assertEquals(0, getControllerData().size());

        mSolo.enterText(mSolo.getEditText(0), tag);

        //assertTrue(mSolo.searchEditText(tag));
        //assertNull(mFragment.getData());

        mSolo.clickOnButton("Done");
        getInstrumentation().waitForIdleSync();

        assertNotNull(mFragment.getData());
        assertEquals(tag, mFragment.getData().getComment());
        assertEquals(0, getControllerData().size());
        assertFalse(dialogIsShowing());
    }

    @SmallTest
    public void testQuickSet() {
        assertEquals(0, getControllerData().size());

        launchDialogAndTestShowing(true);

        String tag1 = RandomStringGenerator.nextWord();
        mSolo.enterText(mSolo.getEditText(0), tag1);
        mSolo.clickOnButton("Add");

        String tag2 = RandomStringGenerator.nextWord();
        mSolo.enterText(mSolo.getEditText(0), tag2);
        mSolo.clickOnButton("Add");

        String tag3 = RandomStringGenerator.nextWord();
        mSolo.enterText(mSolo.getEditText(0), tag3);
        mSolo.clickOnButton("Done");

        getInstrumentation().waitForIdleSync();

        GvCommentList data = (GvCommentList) getControllerData();
        assertEquals(3, data.size());
        assertEquals(tag1, data.getItem(0).getComment());
        assertEquals(tag2, data.getItem(1).getComment());
        assertEquals(tag3, data.getItem(2).getComment());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDialog = new ConfigGvDataAddEditDisplay.AddComment();
        mFragment = new DialogAddListener<>();
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mFragment, DIALOG_TAG);
        ft.commit();

        mController = Administrator.get(getInstrumentation().getTargetContext())
                .createNewReviewInProgress();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    private void launchDialogAndTestShowing(boolean quickSet) {
        Bundle args = getControllerBundle();
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, quickSet);

        assertFalse(dialogIsShowing());

        LauncherUI.launch(mDialog, mFragment, REQUEST_CODE, DIALOG_TAG, args);
        getInstrumentation().waitForIdleSync();

        assertTrue(dialogIsShowing());
    }

    private boolean dialogIsShowing() {
        return mSolo.searchButton("Cancel") &&
                mSolo.searchButton("Add") &&
                mSolo.searchButton("Done") &&
                mSolo.searchText(GvDataList.GvType.COMMENTS.getDatumString());
    }

    private GvDataList getControllerData() {
        return mController.getData(GvDataList.GvType.COMMENTS);
    }

    private Bundle getControllerBundle() {
        return Administrator.get(getInstrumentation().getTargetContext()).pack(mController);
    }
}

