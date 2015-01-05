/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Fragment;
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
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.LaunchableUI;
import com.chdryra.android.reviewer.LauncherUI;
import com.chdryra.android.testutils.RandomStringGenerator;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * More of a black-box behaviour (integration) test than unit test
 */
public class DialogFragmentGvDataAddTagTest extends ActivityInstrumentationTestCase2<ActivityFeed> {
    private static final int    REQUEST_CODE = 1976;
    private static final String DIALOG_TAG   = "TestAddDialog";

    private Solo                              mSolo;
    private ConfigGvDataAddEditDisplay.AddTag mDialog;
    private AddListener                       mFragment;
    private ControllerReview                  mController;

    public DialogFragmentGvDataAddTagTest() {
        super(ActivityFeed.class);
    }

    @SmallTest
    public void testCancelButton() {
        launchDialogAndTestShowing(mDialog, false);

        String tag = RandomStringGenerator.nextWord();
        assertFalse(mSolo.searchEditText(tag));

        mSolo.enterText(mSolo.getEditText(0), tag);

        assertTrue(mSolo.searchEditText(tag));
        assertNull(mFragment.mData);

        mSolo.clickOnButton("Cancel");
        getInstrumentation().waitForIdleSync();

        assertNull(mFragment.mData);
        assertFalse(dialogIsShowing());
    }

    @SmallTest
    public void testAddButtonNotQuickSet() {
        launchDialogAndTestShowing(mDialog, false);

        String tag = RandomStringGenerator.nextWord();
        assertFalse(mSolo.searchEditText(tag));
        assertEquals(0, getControllerData().size());

        mSolo.enterText(mSolo.getEditText(0), tag);

        assertTrue(mSolo.searchEditText(tag));
        assertNull(mFragment.mData);

        mSolo.clickOnButton("Add");
        getInstrumentation().waitForIdleSync();

        assertNotNull(mFragment.mData);
        assertEquals(tag, mFragment.mData.get());
        assertEquals(0, getControllerData().size());
        assertTrue(dialogIsShowing());
        assertTrue(mSolo.getEditText(0).getText().toString().length() == 0);
    }

    @SmallTest
    public void testDoneButtonNotQuickSet() {
        launchDialogAndTestShowing(mDialog, false);

        String tag = RandomStringGenerator.nextWord();
        assertFalse(mSolo.searchEditText(tag));
        assertEquals(0, getControllerData().size());

        mSolo.enterText(mSolo.getEditText(0), tag);

        assertTrue(mSolo.searchEditText(tag));
        assertNull(mFragment.mData);

        mSolo.clickOnButton("Done");
        getInstrumentation().waitForIdleSync();

        assertNotNull(mFragment.mData);
        assertEquals(tag, mFragment.mData.get());
        assertEquals(0, getControllerData().size());
        assertFalse(dialogIsShowing());
    }

    @SmallTest
    public void testQuickSet() {
        assertEquals(0, getControllerData().size());

        launchDialogAndTestShowing(mDialog, true);

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

        GvTagList data = getControllerData();
        assertEquals(3, data.size());
        assertEquals(tag1, data.getItem(0).get());
        assertEquals(tag2, data.getItem(1).get());
        assertEquals(tag3, data.getItem(2).get());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mDialog = new ConfigGvDataAddEditDisplay.AddTag();
        mFragment = new AddListener();
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(mFragment, DIALOG_TAG);
        ft.commit();

        mController = Administrator.get(getInstrumentation().getTargetContext())
                .createNewReviewInProgress();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    private void launchDialogAndTestShowing(LaunchableUI dialog, boolean quickSet) {
        Bundle args = getControllerBundle();
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, quickSet);

        assertFalse(dialogIsShowing());

        LauncherUI.launch(dialog, mFragment, REQUEST_CODE, DIALOG_TAG, args);
        getInstrumentation().waitForIdleSync();

        assertTrue(dialogIsShowing());
    }

    private boolean dialogIsShowing() {
        return mSolo.searchButton("Cancel") &&
                mSolo.searchButton("Add") &&
                mSolo.searchButton("Done") &&
                mSolo.searchText(GvDataList.GvType.TAGS.getDatumString());
    }

    private GvTagList getControllerData() {
        return (GvTagList) mController.getData(GvDataList.GvType.TAGS);
    }

    private Bundle getControllerBundle() {
        return Administrator.get(getInstrumentation().getTargetContext()).pack(mController);
    }

    public static class AddListener extends Fragment implements DialogFragmentGvDataAdd
            .GvDataAddListener<GvTagList.GvTag> {
        GvTagList.GvTag mData;

        @Override
        public boolean onGvDataAdd(GvTagList.GvTag data) {
            if (data != null) {
                mData = data;
                return true;
            } else {
                return false;
            }
        }
    }
}
