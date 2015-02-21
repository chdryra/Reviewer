/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;
import com.chdryra.android.reviewer.ActivityReviewView;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.CommentFormatter;
import com.chdryra.android.reviewer.ConfigGvDataUi;
import com.chdryra.android.reviewer.FragmentReviewView;
import com.chdryra.android.reviewer.GvBuildReviewList;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ReviewBuilder;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.chdryra.android.testutils.CallBackSignaler;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReviewTest extends ActivityReviewViewTest {
    private static final int               NUM_DATA = 3;
    private static final int               TIMEOUT  = 10000;
    private static final GvDataList.GvType TYPE     = GvDataList.GvType.BUILD_REVIEW;
    private static final int               AVERAGE  = R.id.menu_item_average_rating;

    private Administrator     mAdmin;
    private GvBuildReviewList mList;
    private String            mOriginalSubject;
    private float             mOriginalRating;
    private CallBackSignaler  mSignaler;

    public ActivityBuildReviewTest() {
        super(TYPE, true);
    }

    @SmallTest
    public void testSubjectRatingChange() {
        GvChildrenList.GvChildReview child = editSubjectRating();

        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkAdapterSubjectRating(child.getSubject(), child.getRating());
    }

    @SmallTest
    public void testLabels() {
        for (GvBuildReviewList.GvBuildReview dataType : mList) {
            assertTrue(mSolo.searchText(dataType.getGvType().getDataString()));
        }
    }

    @SmallTest
    public void testShareButton() {
        Instrumentation.ActivityMonitor monitor = getActivityMonitor();

        clickShare();

        String toast = mActivity.getResources().getString(R.string.toast_enter_subject);
        assertTrue(mSolo.waitForText(toast));

        GvChildrenList.GvChildReview review = editSubjectRating();
        mOriginalSubject = review.getSubject();
        mOriginalRating = review.getRating();

        clickShare();

        toast = mActivity.getResources().getString(R.string.toast_enter_tag);
        assertTrue(mSolo.waitForText(toast));

        testClickWithoutData(GvDataList.GvType.TAGS, 1);

        clickShare();
        getInstrumentation().waitForIdleSync();

        ActivityReviewView shareActivity = (ActivityReviewView) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(shareActivity);
        assertEquals(ActivityReviewView.class, shareActivity.getClass());
        assertTrue(mSolo.searchText(getActivity().getResources().getString(R
                .string.button_social)));
        mSolo.clickOnActionBarHomeButton();
        mSolo.sleep(1000);
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

    @SmallTest
    public void testTagEntrySingle() {
        testClickGridCell(GvDataList.GvType.TAGS, 1);
    }

    @SmallTest
    public void testCriteriaEntrySingle() {
        testClickGridCell(GvDataList.GvType.CHILDREN, 1);
    }

    @SmallTest
    public void testCommentEntrySingle() {
        testClickGridCell(GvDataList.GvType.COMMENTS, 1);
    }

    @SmallTest
    public void testFactEntrySingle() {
        testClickGridCell(GvDataList.GvType.FACTS, 1);
    }

    @SmallTest
    public void testLocationEntrySingle() {
        testClickGridCell(GvDataList.GvType.LOCATIONS, 1);
    }

    @SmallTest
    public void testTagEntryMulti() {
        testClickGridCell(GvDataList.GvType.TAGS, NUM_DATA);
    }

    @SmallTest
    public void testCriteriaEntryMulti() {
        testClickGridCell(GvDataList.GvType.CHILDREN, NUM_DATA);
    }

    @SmallTest
    public void testCommentEntryMulti() {
        testClickGridCell(GvDataList.GvType.COMMENTS, NUM_DATA);
    }

    @SmallTest
    public void testFactEntryMulti() {
        testClickGridCell(GvDataList.GvType.FACTS, NUM_DATA);
    }

    @SmallTest
    public void testImagesClick() {
        int position = getItemPosition(GvDataList.GvType.IMAGES);
        mSolo.clickInList(position + 1);

        getInstrumentation().waitForIdleSync();

        assertTrue(mSolo.searchText("Select Source"));
        assertTrue(mSolo.searchText("Camera"));
        assertTrue(mSolo.searchText("Gallery"));

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
        mSolo.sleep(1000);
    }

    @SmallTest
    public void testMenuAverage() {
        GvChildrenList.GvChildReview child = editSubjectRating();
        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkAdapterSubjectRating(child.getSubject(), child.getRating());
        assertFalse(getBuilder().isRatingAverage());

        mSolo.clickOnActionBarItem(AVERAGE);

        checkAdapterSubjectRating(child.getSubject(), 0);
        checkFragmentSubjectRating(child.getSubject(), 0);
        assertTrue(getBuilder().isRatingAverage());
        assertEquals(0, mAdapter.getAverageRating());

        child = editSubjectRating();

        mOriginalSubject = child.getSubject();
        mOriginalRating = child.getRating();
        assertFalse(getBuilder().isRatingAverage());

        testClickWithoutData(GvDataList.GvType.CHILDREN, NUM_DATA);

        assertFalse(getBuilder().isRatingAverage());
        while (child.getRating() == getAverageRating(true)) child = editSubjectRating();
        assertFalse(child.getRating() == getAverageRating(true));

        mSolo.clickOnActionBarItem(AVERAGE);
        checkFragmentSubjectRating(child.getSubject(), getAverageRating(true));
        checkAdapterSubjectRating(child.getSubject(), getAverageRating(false));
        assertTrue(getBuilder().isRatingAverage());
        assertEquals(getAverageRating(false), mAdapter.getAverageRating());
    }

    protected void checkFragmentSubjectRating(String subject, float rating) {
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(subject, fragment.getSubject());
        assertEquals(rating, fragment.getRating());
    }

    protected void checkAdapterSubjectRating(String subject, float rating) {
        assertEquals(subject, mAdapter.getSubject());
        assertEquals(rating, mAdapter.getRating(), 0.01);
    }

    @Override
    protected void setAdapter() {
        mAdapter = mAdmin.newReviewBuilder(mActivity);
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    @Override
    protected void setUp() {
        mAdmin = Administrator.get(getInstrumentation().getTargetContext());
        super.setUp();

        mList = (GvBuildReviewList) mAdapter.getGridData();
        mOriginalSubject = mAdapter.getSubject();
        mOriginalRating = mAdapter.getRating();

        checkSubjectRating();
        checkControllerChanges(null);

        mSignaler = new CallBackSignaler(5);
    }

    protected void checkControllerDataChanges(GvDataList data) {
        testInBuilder(data, true);
        checkControllerChanges(data.getGvType());
    }

    protected void enterData(GvDataList data, String tag) {
        for (int i = 0; i < data.size() - 1; ++i) {
            SoloDataEntry.enter(mSolo, (GvDataList.GvData) data.getItem(i));
            clickActionButton(tag);
        }

        SoloDataEntry.enter(mSolo, ((GvDataList.GvData) data.getItem(data.size() - 1)));
        clickDoneButton(tag);
    }

    private ReviewBuilder getBuilder() {
        return (ReviewBuilder) mAdapter;
    }

    private float getAverageRating(boolean nearestHalf) {
        GvChildrenList children = (GvChildrenList) getBuilder().getDataBuilder(GvDataList.GvType
                .CHILDREN).getGridData();

        float rating = children.getAverageRating();
        return nearestHalf ? Math.round(rating * 2f) / 2f : rating;
    }

    private void testInBuilder(GvDataList data, boolean result) {
        GvDataList fromBuilder = getBuilder().getDataBuilder(data.getGvType()).getGridData();
        fromBuilder.sort();
        data.sort();
        if (data.getGvType() == GvDataList.GvType.LOCATIONS) {
            testInBuilderLocationNames(data, fromBuilder, result);
        } else {
            assertEquals(result, data.equals(fromBuilder));
        }
    }

    private void testInBuilderLocationNames(GvDataList data, GvDataList fromBuilder,
            boolean result) {
        //LatLng is current latlng set by phone GPS so can only compare names
        GvLocationList dataLocations = (GvLocationList) data;
        GvLocationList builderLocations = (GvLocationList) fromBuilder;
        if (!result && dataLocations.size() == builderLocations.size()) {
            for (int i = 0; i < dataLocations.size(); ++i) {
                assertFalse(dataLocations.getItem(i).getName().equals(
                        builderLocations.getItem(i).getName()));
            }
        } else if (result) {
            assertEquals(dataLocations.size(), builderLocations.size());
            for (int i = 0; i < dataLocations.size(); ++i) {
                assertEquals(dataLocations.getItem(i).getName(),
                        builderLocations.getItem(i).getName());
            }
        }
    }

    private void checkCell(View cell, GvDataList data, boolean inCell) {
        int num = data.size();
        ArrayList<TextView> tvs = mSolo.getCurrentViews(TextView.class, cell);
        if (!inCell) {
            checkNoDataCell(data.getGvType(), tvs);
        } else if (num == 1) {
            checkDatumCell(cell, data);
        } else if (num > 1) {
            checkDataCell(data, tvs);
        }
    }

    private void checkDatumCell(View cell, GvDataList data) {
        assertEquals(1, data.size());
        switch (data.getGvType()) {
            case TAGS:
                ArrayList<TextView> tvs = mSolo.getCurrentViews(TextView.class, cell);
                GvTagList.GvTag tag = (GvTagList.GvTag) data.getItem(0);
                assertEquals(1, tvs.size());
                assertEquals(tag.get(), tvs.get(0).getText().toString());
                break;
            case COMMENTS:
                tvs = mSolo.getCurrentViews(TextView.class, cell);
                GvCommentList.GvComment comment = (GvCommentList.GvComment) data.getItem(0);
                assertEquals(1, tvs.size());
                String headline = CommentFormatter.getHeadline(comment.getComment());
                assertEquals(headline, tvs.get(0).getText().toString());
                break;
            case FACTS:
                tvs = mSolo.getCurrentViews(TextView.class, cell);
                GvFactList.GvFact fact = (GvFactList.GvFact) data.getItem(0);
                assertEquals(2, tvs.size());
                assertEquals(fact.getLabel(), tvs.get(0).getText().toString());
                assertEquals(fact.getValue(), tvs.get(1).getText().toString());
                break;
            case LOCATIONS:
                tvs = mSolo.getCurrentViews(TextView.class, cell);
                GvLocationList.GvLocation location = (GvLocationList.GvLocation) data.getItem(0);
                assertEquals(1, tvs.size());
                assertEquals("@" + location.getName(), tvs.get(0).getText().toString());
                break;
            case CHILDREN:
                tvs = mSolo.getCurrentViews(TextView.class, cell);
                ArrayList<RatingBar> bars = mSolo.getCurrentViews(RatingBar.class, cell);
                GvChildrenList.GvChildReview childReview = (GvChildrenList.GvChildReview) data
                        .getItem(0);
                assertEquals(1, tvs.size());
                assertEquals(1, bars.size());
                assertEquals(childReview.getSubject(), tvs.get(0).getText().toString());
                assertEquals(childReview.getRating(), bars.get(0).getRating());
                break;
            case IMAGES:
                tvs = mSolo.getCurrentViews(TextView.class, cell);
                GvImageList.GvImage image = (GvImageList.GvImage) data.getItem(0);
                assertEquals(2, tvs.size());
                assertEquals(String.valueOf(1), tvs.get(0).getText().toString());
                assertEquals(GvDataList.GvType.IMAGES.getDatumString(),
                        tvs.get(1).getText().toString());
                break;
            default:
                break;
        }
    }

    private void checkNoDataCell(GvDataList.GvType dataType, ArrayList<TextView> tvs) {
        assertEquals(1, tvs.size());
        assertEquals(dataType.getDataString(), tvs.get(0).getText().toString());
    }

    private void checkDataCell(GvDataList data, ArrayList<TextView> tvs) {
        assertEquals(2, tvs.size());
        assertEquals(String.valueOf(data.size()), tvs.get(0).getText().toString());
        assertEquals(data.getGvType().getDataString(), tvs.get(1).getText().toString());
    }

    private void testInGrid(GvDataList data, boolean inGrid) {
        int position = getItemPosition(data.getGvType());
        checkCell(getGridView().getChildAt(position), data, inGrid);
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

    private void testClickGridCell(GvDataList.GvType dataType, int numData) {
        testClickWithoutData(dataType, numData);
        testClickWithData(dataType);
    }

    private int getItemPosition(GvDataList.GvType dataType) {
        int position = -1;
        for (int i = 0; i < mList.size(); ++i) {
            if (mList.getItem(i).getGvType() == dataType) {
                position = i;
                break;
            }
        }

        assertTrue(position > -1);
        return position;
    }

    private void testClickWithoutData(GvDataList.GvType dataType, int numData) {
        final GvDataList data = GvDataMocker.getData(dataType, numData);

        testInBuilder(data, false);
        testInGrid(data, false);

        testDialogShowing(false);

        mSolo.clickOnText(dataType.getDataString());

        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000); //need to do this due to UI thread is separate to test thread

        testDialogShowing(true);

        String tag = ConfigGvDataUi.getConfig(dataType).getAdderConfig().getTag();
        enterData(data, tag);

        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);

        testDialogShowing(false);

        testInGrid(data, true);
        checkSubjectRating();
        checkControllerDataChanges(data);
    }

    private void testClickWithData(GvDataList.GvType dataType) {
        Instrumentation.ActivityMonitor monitor = testEditScreenNotShowing(dataType);
        int position = getItemPosition(dataType);
        mSolo.clickInList(position + 1);
        testEditScreenShowing(dataType, monitor);
        mSolo.clickOnActionBarHomeButton();
        mSolo.sleep(1000);
    }

    private DialogCancelActionDoneFragment getDialog(String tag) {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(tag);
        return (DialogCancelActionDoneFragment) f;
    }

    private void clickActionButton(final String tag) {
        Runnable clicker = new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getDialog(tag).clickActionButton();
                mSignaler.signal();
            }
        };

        try {
            runTestOnUiThread(clicker);
            mSignaler.waitForSignal();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void clickDoneButton(final String tag) {
        Runnable clicker = new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getDialog(tag).clickDoneButton();
                mSignaler.signal();
            }
        };

        try {
            runTestOnUiThread(clicker);
            mSignaler.waitForSignal();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Instrumentation.ActivityMonitor getActivityMonitor() {
        return getInstrumentation().addMonitor(ActivityReviewView
                .class.getName(), null, false);
    }

    private Instrumentation.ActivityMonitor testEditScreenNotShowing(GvDataList.GvType dataType) {
        assertFalse(mSolo.searchText("Add " + dataType.getDataString()));
        return getActivityMonitor();
    }

    private void testEditScreenShowing(GvDataList.GvType dataType,
            Instrumentation.ActivityMonitor monitor) {
        ActivityReviewView editActivity = (ActivityReviewView) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(editActivity);
        assertEquals(ActivityReviewView.class, editActivity.getClass());

        getInstrumentation().waitForIdleSync();
        assertTrue(mSolo.searchText("Add " + dataType.getDatumString()));
    }

    private void testLongPress(GvDataList.GvType dataType) {
        Instrumentation.ActivityMonitor monitor = testEditScreenNotShowing(dataType);

        mSolo.clickLongOnText(dataType.getDataString());

        testEditScreenShowing(dataType, monitor);
        mSolo.clickOnActionBarHomeButton();
        mSolo.sleep(1000);
    }

    private void checkControllerChanges(GvDataList.GvType dataType) {
        for (GvBuildReviewList.GvBuildReview type : mList) {
            if (dataType != null && type.getGvType() == dataType) continue;
            assertEquals(0, getBuilder().getDataSize(type.getGvType()));
        }
    }

    private void checkSubjectRating() {
        checkAdapterSubjectRating();
        checkFragmentSubjectRating();
    }

    private void checkAdapterSubjectRating() {
        checkAdapterSubjectRating(mOriginalSubject, mOriginalRating);
    }

    private void checkFragmentSubjectRating() {
        checkFragmentSubjectRating(mOriginalSubject, mOriginalRating);
    }

    private GvChildrenList.GvChildReview editSubjectRating() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());

        //Kind of simulate touch
        mSolo.clickOnView(mSolo.getView(R.id.rating_bar));
        assertFalse(getBuilder().isRatingAverage());
        assertEquals(3.0f, getBuilder().getRating());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    private void clickShare() {
        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_share));
    }
}
