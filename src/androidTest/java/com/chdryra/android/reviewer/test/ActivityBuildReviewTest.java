/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.FragmentViewReview;
import com.chdryra.android.reviewer.GvBuildReviewList;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReviewTest extends ActivityViewReviewTest {
    private static final int               NUM_DATA = 3;
    private static final int               TIMEOUT  = 10000;
    private static final GvDataList.GvType TYPE     = GvDataList.GvType.BUILD_REVIEW;
    private Administrator     mAdmin;
    private GvBuildReviewList mList;
    private String            mOriginalSubject;
    private float             mOriginalRating;

    public ActivityBuildReviewTest() {
        super(TYPE, true);
    }

    @SmallTest
    public void testSubjectRatingChange() {
        GvChildrenList.GvChildReview child = editSubjectRating();

        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkControllerSubjectRating(child.getSubject(), child.getRating());
    }

    @SmallTest
    public void testLabels() {
        for (GvBuildReviewList.GvBuildReview dataType : mList) {
            assertTrue(mSolo.searchText(dataType.getGvType().getDataString()));
        }
    }

    @SmallTest
    public void testAddTags() {

    }

    @SmallTest
    public void testShareButton() {
        Instrumentation.ActivityMonitor monitor = getActivityMonitor();

        clickShare();

        String toast = mActivity.getResources().getString(R.string.toast_enter_subject);
        assertTrue(mSolo.waitForText(toast));

        editSubjectRating();

        clickShare();

        toast = mActivity.getResources().getString(R.string.toast_enter_tag);
        assertTrue(mSolo.waitForText(toast));
//
//        getInstrumentation().waitForIdleSync();
//
//        ActivityViewReview shareActivity = (ActivityFeed) monitor.waitForActivityWithTimeout
//                (TIMEOUT);
//        assertNotNull(shareActivity);
//        assertEquals(ActivityViewReview.class, shareActivity.getClass());
//        assertTrue(mSolo.searchText(getActivity().getResources().getString(R
//                .string.button_social)));
    }

    @SmallTest
    public void testTagsLongPress() {
        testLongPress(GvDataList.GvType.TAGS);
    }

    @SmallTest
    public void testCriteriaLongPress() {
        testLongPress(GvDataList.GvType.CHILDREN);
    }

    @SmallTest
    public void testImagesLongPress() {
        testLongPress(GvDataList.GvType.IMAGES);
    }

    @SmallTest
    public void testCommentsLongPress() {
        testLongPress(GvDataList.GvType.COMMENTS);
    }

    @SmallTest
    public void testLocationsLongPress() {
        testLongPress(GvDataList.GvType.LOCATIONS);
    }

    @SmallTest
    public void testFactsLongPress() {
        testLongPress(GvDataList.GvType.FACTS);
    }

    protected void checkFragmentSubjectRating(String subject, float rating) {
        FragmentViewReview fragment = getFragmentViewReview();
        assertEquals(subject, fragment.getSubject());
        assertEquals(rating, fragment.getRating());
    }

    protected void checkControllerSubjectRating(String subject, float rating) {
        assertEquals(subject, mController.getSubject());
        assertEquals(rating, mController.getRating());
    }

    @Override
    protected ControllerReview getController() {
        return mAdmin.createNewReviewInProgress();
    }

    @Override
    protected void setUp() {
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        super.setUp();
        mList = GvBuildReviewList.newInstance(getActivity(), mController);

        mOriginalSubject = mController.getSubject();
        mOriginalRating = mController.getRating();
        checkSubjectRating();
        checkControllerChanges(null);
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentViewReview fragment = getFragmentViewReview();
        assertEquals(mController.getSubject(), fragment.getSubject());
        assertEquals(mController.getRating(), fragment.getRating());
    }

    protected void checkControllerDataChanges(GvDataList data) {
        testInController(data, true);
        checkControllerChanges(data.getGvType());
    }

    private void testInController(GvDataList data, boolean inController) {
        GvDataList fromController = mController.getData(data.getGvType());
        fromController.sort();
        data.sort();
        assertEquals(inController, data.equals(fromController));
    }

    private void testInGrid(GvDataList.GvType dataType, boolean inGrid) {
        if (inGrid) {
            assertTrue(mSolo.searchText(String.valueOf(NUM_DATA), 1));
        } else {
            assertFalse(mSolo.searchText(String.valueOf(NUM_DATA)));
        }
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

    private void testClick(GvDataList.GvType dataType) {
        final GvDataList data = GvDataMocker.getData(dataType, NUM_DATA);
        ;
        testInController(data, false);
        testInGrid(dataType, false);

        testDialogShowing(false);

        mSolo.clickOnText(dataType.getDataString());

        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000); //need to do this due to UI thread is separate to test thread

        testDialogShowing(true);

        //enterData(data);

        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);

        testDialogShowing(false);

        testInGrid(dataType, true);
        checkSubjectRating();
        checkControllerDataChanges(data);
    }

    private Instrumentation.ActivityMonitor getActivityMonitor() {
        return getInstrumentation().addMonitor(ActivityViewReview
                .class.getName(), null, false);
    }

    private void testLongPress(GvDataList.GvType dataType) {
        assertFalse(mSolo.searchText("Add " + dataType.getDataString()));

        Instrumentation.ActivityMonitor monitor = getActivityMonitor();
        mSolo.clickLongOnText(dataType.getDataString());

        ActivityViewReview editActivity = (ActivityViewReview) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(editActivity);
        assertEquals(ActivityViewReview.class, editActivity.getClass());

        getInstrumentation().waitForIdleSync();
        assertTrue(mSolo.searchText("Add " + dataType.getDatumString()));
    }

    private void checkControllerChanges(GvDataList.GvType dataType) {
        for (GvBuildReviewList.GvBuildReview type : mList) {
            if (dataType != null && type.getGvType() == dataType) continue;
            assertEquals(0, mController.getData(type.getGvType()).size());
        }
    }

    private void checkSubjectRating() {
        checkControllerSubjectRating();
        checkFragmentSubjectRating();
    }

    private void checkControllerSubjectRating() {
        checkControllerSubjectRating(mOriginalSubject, mOriginalRating);
    }

    private void checkFragmentSubjectRating() {
        checkFragmentSubjectRating(mOriginalSubject, mOriginalRating);
    }

    private GvChildrenList.GvChildReview editSubjectRating() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    private void clickShare() {
        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_share));
    }
}
