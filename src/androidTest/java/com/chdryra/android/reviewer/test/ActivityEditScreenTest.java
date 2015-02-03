/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.GridView;

import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReviewEditable;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityEditScreenTest extends
        ActivityInstrumentationTestCase2<ActivityViewReview> {
    private static final int NUM_DATA = 3;
    private static final int DELETE   = com.chdryra.android.reviewer.R.id
            .menu_item_delete;
    private static final int DONE     = com.chdryra.android.reviewer.R.id
            .menu_item_done;
    protected Solo                     mSolo;
    private   GvDataList.GvType        mDataType;
    private   ControllerReviewEditable mController;

    protected abstract void enterData(GvDataList data);

    public ActivityEditScreenTest(GvDataList.GvType dataType) {
        super(ActivityViewReview.class);
        mDataType = dataType;
    }

    @SmallTest
    public void testActivityLaunches() {
        assertTrue(mSolo.searchText(mDataType.getDatumString()));
        mSolo.searchText(mDataType.getDataString());
        mSolo.searchText("add " + mDataType.getDatumString());
    }

    @SmallTest
    public void testSubjectRatingChange() {
        String reviewSubject = mController.getSubject();
        String currentSubject = mSolo.getEditText(0).getText().toString();
        assertEquals(reviewSubject, currentSubject);

        final GvChildrenList.GvChildReview child = GvDataMocker.newChild();

        mSolo.clearEditText(0);
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        mSolo.clickOnActionBarItem(DONE);
        assertEquals(mController.getSubject(), child.getSubject());
        assertEquals(mController.getRating(), child.getRating());
    }

    @SmallTest
    public void testBannerButtonAdd() {
        GvDataList data = GvDataMocker.getData(mDataType, NUM_DATA);
        testInController(data, false);

        testInGrid(data, false);

        testDialogShowing(false);
        mSolo.clickOnButton("Add " + mDataType.getDatumString());
        testDialogShowing(true);

        enterData(data);

        testDialogShowing(false);

        testInGrid(data, true);

        mSolo.clickOnActionBarItem(DONE);
        testInController(data, true);
    }

    @SmallTest
    public void testPreexistingDataShows() {
        GvDataList currentData = mController.getData(mDataType);
        assertEquals(0, currentData.size());
        GvDataList data = GvDataMocker.getData(mDataType, NUM_DATA);
        mController.setData(data);
        currentData = mController.getData(mDataType);
        assertEquals(data, currentData);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent i = new Intent();
        ActivityViewReview.packParameters(mDataType, true, i);
        mController = new ControllerReviewEditable(ReviewMocker.newReviewEditable());
        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        admin.pack(mController, i);
        setActivityIntent(i);
        Activity activity = getActivity();
        mSolo = new Solo(getInstrumentation(), activity);
    }

    private void testInController(GvDataList data, boolean inController) {
        data.sort();
        assertEquals(inController, data.equals(mController.getData(mDataType)));
    }

    private void testDialogShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchButton("Cancel"));
            assertTrue(mSolo.searchButton("Done"));
        } else {
            assertFalse(mSolo.searchButton("Cancel"));
            assertFalse(mSolo.searchButton("Done"));
        }
    }

    private void testInGrid(GvDataList data, boolean isInGrid) {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        GridView gridView = (GridView) views.get(0);
        if (isInGrid) {
            assertEquals(data.size(), gridView.getAdapter().getCount());
        } else {
            assertEquals(0, gridView.getAdapter().getCount());
            return;
        }

        data.sort();
        for (int i = 0; i < data.size(); ++i) {
            GvDataList.GvData datumExpected = (GvDataList.GvData) data.getItem(i);
            GvDataList.GvData datumGrid = (GvDataList.GvData) gridView.getAdapter().getItem(i);
            assertEquals(datumExpected, datumGrid);
        }
    }
}

