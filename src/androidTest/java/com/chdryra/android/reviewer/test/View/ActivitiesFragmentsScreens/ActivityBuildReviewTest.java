/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Instrumentation;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.WrapperGridData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.BuildScreen;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Utils.CommentFormatter;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.chdryra.android.testutils.CallBackSignaler;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReviewTest extends ActivityReviewViewTest {
    private static final int NUM_DATA = 3;
    private static final int TIMEOUT = 10000;
    private static final int AVERAGE = R.id.menu_item_average_rating;

    private WrapperGridData mList;
    private String mOriginalSubject;
    private float mOriginalRating;
    private CallBackSignaler mSignaler;
    private BuildScreen mScreen;

    @SmallTest
    public void testSubjectRatingChange() {
        GvCriterionList.GvCriterion child = editSubjectRating();

        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkAdapterSubjectRating(child.getSubject(), child.getRating());
    }

    @SmallTest
    public void testLabels() {
        for (WrapperGridData.GvBuildReview dataType : mList) {
            assertTrue(mSolo.searchText(dataType.getGvDataType().getDataName()));
        }
    }

    @SmallTest
    public void testShareButton() {
        Instrumentation.ActivityMonitor monitor = getActivityMonitor();

        clickShare();

        String toast = mActivity.getResources().getString(R.string.toast_enter_subject);
        assertTrue(mSolo.waitForText(toast));

        GvCriterionList.GvCriterion review = editSubjectRating();
        mOriginalSubject = review.getSubject();
        mOriginalRating = review.getRating();

        testDialogShowing(false);

        clickShare();

        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000); //need to do this due to UI thread is separate to test thread
        testDialogShowing(true);
        final GvDataList data = GvDataMocker.getData(GvTagList.GvTag.TYPE, 1);
        String tag = ConfigGvDataUi.getConfig(GvTagList.GvTag.TYPE).getAdderConfig().getTag();
        enterData(data, tag, false);
        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);
        testDialogShowing(false);

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
        testLongPress(GvTagList.GvTag.TYPE);
    }

    @SmallTest
    public void testCriteriaLongPress() {
        testLongPress(GvCriterionList.GvCriterion.TYPE);
    }

    @SmallTest
    public void testImagesLongPress() {
        testLongPress(GvImageList.GvImage.TYPE);
    }

    @SmallTest
    public void testCommentsLongPress() {
        testLongPress(GvCommentList.GvComment.TYPE);
    }

    @SmallTest
    public void testLocationsLongPress() {
        testLongPress(GvLocationList.GvLocation.TYPE);
    }

    @SmallTest
    public void testFactsLongPress() {
        testLongPress(GvFactList.GvFact.TYPE);
    }

    @SmallTest
    public void testTagEntrySingle() {
        testClickGridCell(GvTagList.GvTag.TYPE, 1);
    }

    @SmallTest
    public void testCriteriaEntrySingle() {
        testClickGridCell(GvCriterionList.GvCriterion.TYPE, 1);
    }

    @SmallTest
    public void testCommentEntrySingle() {
        testClickGridCell(GvCommentList.GvComment.TYPE, 1);
    }

    @SmallTest
    public void testFactEntrySingle() {
        testClickGridCell(GvFactList.GvFact.TYPE, 1);
    }

    @SmallTest
    public void testLocationEntrySingle() {
        testClickGridCell(GvLocationList.GvLocation.TYPE, 1, true);
    }

    @SmallTest
    public void testTagEntryMulti() {
        testClickGridCell(GvTagList.GvTag.TYPE, NUM_DATA);
    }

    @SmallTest
    public void testCriteriaEntryMulti() {
        testClickGridCell(GvCriterionList.GvCriterion.TYPE, NUM_DATA);
    }

    @SmallTest
    public void testCommentEntryMulti() {
        testClickGridCell(GvCommentList.GvComment.TYPE, NUM_DATA);
    }

    @SmallTest
    public void testFactEntryMulti() {
        testClickGridCell(GvFactList.GvFact.TYPE, NUM_DATA);
    }

    @SmallTest
    public void testLocationEntryMulti() {
        testClickGridCell(GvLocationList.GvLocation.TYPE, NUM_DATA, true);
    }

    @SmallTest
    public void testImagesClick() {
        int position = getItemPosition(GvImageList.GvImage.TYPE);
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
        GvCriterionList.GvCriterion child = editSubjectRating();
        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkAdapterSubjectRating(child.getSubject(), child.getRating());

        //There are no criteria so should set to zero
        mSolo.clickOnActionBarItem(AVERAGE);

        checkAdapterSubjectRating(child.getSubject(), 0);
        checkFragmentSubjectRating(child.getSubject(), 0);

        child = editSubjectRating();

        mOriginalSubject = child.getSubject();
        mOriginalRating = child.getRating();

        testClickWithoutData(GvCriterionList.GvCriterion.TYPE, NUM_DATA);

        while (child.getRating() == getAverageRating(true)) child = editSubjectRating();
        assertFalse(child.getRating() == getAverageRating(true));

        mSolo.clickOnActionBarItem(AVERAGE);
        checkFragmentSubjectRating(child.getSubject(), getAverageRating(true));
        checkAdapterSubjectRating(child.getSubject(), getAverageRating(false));
    }

//protected methods
    @Override
    protected ReviewView getView() {
        return mScreen.getEditor();
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

    protected void checkBuilderDataChanges(GvDataList data) {
        testInBuilder(data, true);
        checkBuilderChanges(data.getGvDataType());
    }

    protected void enterData(GvDataList data, String tag, boolean entryWithPause) {
        for (int i = 0; i < data.size() - 1; ++i) {
            SoloDataEntry.enter(mSolo, (GvData) data.getItem(i));
            if (entryWithPause) mSolo.sleep(2000);
            clickActionButton(tag);
        }

        SoloDataEntry.enter(mSolo, ((GvData) data.getItem(data.size() - 1)));
        if (entryWithPause) mSolo.sleep(2000);
        clickDoneButton(tag);
    }

    //private methods
    private ReviewBuilderAdapter getBuilder() {
        return (ReviewBuilderAdapter) mAdapter;
    }

    private Instrumentation.ActivityMonitor getActivityMonitor() {
        return getInstrumentation().addMonitor(ActivityReviewView
                .class.getName(), null, false);
    }

    private float getAverageRating(boolean nearestHalf) {
        GvCriterionList children = (GvCriterionList) getBuilder().getDataBuilderAdapter
                (GvCriterionList.GvCriterion.TYPE)
                .getGridData();

        float rating = children.getAverageRating();
        return nearestHalf ? Math.round(rating * 2f) / 2f : rating;
    }

    private void testInBuilder(GvDataList data, boolean result) {
        GvDataList fromBuilder = getBuilder().getDataBuilderAdapter(data.getGvDataType()).getGridData();
        fromBuilder.sort();
        data.sort();
        if (data.getGvDataType() == GvLocationList.GvLocation.TYPE) {
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
            checkNoDataCell(data.getGvDataType(), tvs);
        } else if (num == 1) {
            checkDatumCell(cell, data);
        } else if (num > 1) {
            checkDataCell(data, tvs);
        }
    }

    private void checkDatumCell(View cell, GvDataList data) {
        assertEquals(1, data.size());
        if (data.getGvDataType().equals(GvTagList.GvTag.TYPE)) {
            ArrayList<TextView> tvs = mSolo.getCurrentViews(TextView.class, cell);
            GvTagList.GvTag tag = (GvTagList.GvTag) data.getItem(0);
            assertEquals(1, tvs.size());
            assertEquals(tag.getString(), tvs.get(0).getText().toString());

        } else if (data.getGvDataType().equals(GvCommentList.GvComment.TYPE)) {
            ArrayList<TextView> tvs;
            tvs = mSolo.getCurrentViews(TextView.class, cell);
            GvCommentList.GvComment comment = (GvCommentList.GvComment) data.getItem(0);
            assertEquals(1, tvs.size());
            String headline = CommentFormatter.getHeadline(comment.getComment());
            assertEquals(headline, tvs.get(0).getText().toString());

        } else if (data.getGvDataType().equals(GvFactList.GvFact.TYPE)) {
            ArrayList<TextView> tvs;
            tvs = mSolo.getCurrentViews(TextView.class, cell);
            GvFactList.GvFact fact = (GvFactList.GvFact) data.getItem(0);
            assertEquals(2, tvs.size());
            assertEquals(fact.getLabel(), tvs.get(0).getText().toString());
            assertEquals(fact.getValue(), tvs.get(1).getText().toString());

        } else if (data.getGvDataType().equals(GvLocationList.GvLocation.TYPE)) {
            ArrayList<TextView> tvs;
            tvs = mSolo.getCurrentViews(TextView.class, cell);
            GvLocationList.GvLocation location = (GvLocationList.GvLocation) data.getItem(0);
            assertEquals(1, tvs.size());
            assertEquals(location.getName(), tvs.get(0).getText().toString());

        } else if (data.getGvDataType().equals(GvCriterionList.GvCriterion.TYPE)) {
            ArrayList<TextView> tvs;
            tvs = mSolo.getCurrentViews(TextView.class, cell);
            ArrayList<RatingBar> bars = mSolo.getCurrentViews(RatingBar.class, cell);
            GvCriterionList.GvCriterion childReview = (GvCriterionList.GvCriterion) data
                    .getItem(0);
            assertEquals(1, tvs.size());
            assertEquals(1, bars.size());
            assertEquals(childReview.getSubject(), tvs.get(0).getText().toString());
            assertEquals(childReview.getRating(), bars.get(0).getRating());

        } else if (data.getGvDataType().equals(GvImageList.GvImage.TYPE)) {
            ArrayList<TextView> tvs;
            tvs = mSolo.getCurrentViews(TextView.class, cell);
            assertEquals(2, tvs.size());
            assertEquals(String.valueOf(1), tvs.get(0).getText().toString());
            assertEquals(GvImageList.GvImage.TYPE.getDatumName(),
                    tvs.get(1).getText().toString());

        }
    }

    private void checkNoDataCell(GvDataType dataType, ArrayList<TextView> tvs) {
        assertEquals(2, tvs.size());
        assertEquals("0", tvs.get(0).getText().toString());
        assertEquals(dataType.getDataName(), tvs.get(1).getText().toString());
    }

    private void checkDataCell(GvDataList data, ArrayList<TextView> tvs) {
//        assertEquals(2, tvs.size()); //inconsistent
        assertEquals(String.valueOf(data.size()), tvs.get(0).getText().toString());
        assertEquals(data.getGvDataType().getDataName(), tvs.get(1).getText().toString());
    }

    private void testInGrid(GvDataList data, boolean inGrid) {
        int position = getItemPosition(data.getGvDataType());
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

    private void testClickGridCell(GvDataType dataType, int numData) {
        testClickGridCell(dataType, numData, false);
    }

    private void testClickGridCell(GvDataType dataType, int numData,
                                   boolean entryWithPause) {
        testClickWithoutData(dataType, numData, entryWithPause);
        testClickWithData(dataType);
    }

    private int getItemPosition(GvDataType dataType) {
        int position = -1;
        for (int i = 0; i < mList.size(); ++i) {
            if (mList.getItem(i).getGvDataType() == dataType) {
                position = i;
                break;
            }
        }

        assertTrue(position > -1);
        return position;
    }

    private void testClickWithoutData(GvDataType dataType, int numData) {
        testClickWithoutData(dataType, numData, false);
    }

    private GvDataList getData(GvDataType dataType, int numData) {
        GvDataList data;
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            GvCommentList mocked = GvDataMocker.newCommentList(numData, false);
            GvCommentList comments = new GvCommentList();
            for (int i = 0; i < numData; ++i) {
                boolean isHeadline = i == 0;
                GvCommentList.GvComment mock = mocked.getItem(i);
                comments.add(new GvCommentList.GvComment(mock.getComment(), isHeadline));
            }
            data = comments;
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            GvImageList mocked = GvDataMocker.newImageList(numData, false);
            GvImageList images = new GvImageList();
            for (int i = 0; i < numData; ++i) {
                boolean isCover = i == 0;
                GvImageList.GvImage mock = mocked.getItem(i);
                images.add(new GvImageList.GvImage(mock.getBitmap(), mock.getDate(),
                        mock.getLatLng(), mock.getCaption(), isCover));
            }
            data = images;
        } else {
            data = GvDataMocker.getData(dataType, numData);
        }

        return data;
    }

    private void testClickWithoutData(GvDataType dataType, int numData,
                                      boolean entryWithPause) {
        final GvDataList data = getData(dataType, numData);

        testInBuilder(data, false);
        testInGrid(data, false);

        testDialogShowing(false);

        mSolo.clickOnText(dataType.getDataName());

        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000); //need to do this due to UI thread is separate to test thread

        testDialogShowing(true);

        String tag = ConfigGvDataUi.getConfig(dataType).getAdderConfig().getTag();
        enterData(data, tag, entryWithPause);

        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);

        testDialogShowing(false);

        testInGrid(data, true);
        checkSubjectRating();
        checkBuilderDataChanges(data);
    }

    private void testClickWithData(GvDataType dataType) {
        Instrumentation.ActivityMonitor monitor = testEditScreenNotShowing(dataType);
        int position = getItemPosition(dataType);
        mSolo.clickInList(position + 1);
        testEditScreenShowing(dataType, monitor);
        //mSolo.clickOnActionBarHomeButton();
        mSolo.clickOnView(mSolo.getView(android.R.id.home));
        mSolo.sleep(1000);
    }

    private DialogCancelActionDoneFragment getDialog(String tag) {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(tag);
        return (DialogCancelActionDoneFragment) f;
    }

    private void clickActionButton(final String tag) {
        Runnable clicker = new Runnable() {
            //Overridden
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
//Overridden
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

    private Instrumentation.ActivityMonitor testEditScreenNotShowing(GvDataType
                                                                             dataType) {
        assertFalse(mSolo.searchText("Add " + dataType.getDataName()));
        return getActivityMonitor();
    }

    private void testEditScreenShowing(GvDataType dataType,
                                       Instrumentation.ActivityMonitor monitor) {
        ActivityReviewView editActivity = (ActivityReviewView) monitor.waitForActivityWithTimeout
                (TIMEOUT);
        assertNotNull(editActivity);
        assertEquals(ActivityReviewView.class, editActivity.getClass());

        getInstrumentation().waitForIdleSync();
        assertTrue(mSolo.searchText("Add " + dataType.getDatumName()));
    }

    private void testLongPress(GvDataType dataType) {
        Instrumentation.ActivityMonitor monitor = testEditScreenNotShowing(dataType);

        mSolo.clickLongOnText(dataType.getDataName());

        testEditScreenShowing(dataType, monitor);
        mSolo.clickOnView(mSolo.getView(android.R.id.home));
        mSolo.sleep(1000);
    }

    private void checkBuilderChanges(GvDataType dataTypeToIgnore) {
        for (WrapperGridData.GvBuildReview type : mList) {
            GvDataType dataType = type.getGvDataType();
            if (dataTypeToIgnore != null && dataType == dataTypeToIgnore) continue;
            assertEquals(0, getBuilder().getDataBuilderAdapter(dataType).getGridData().size());
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

    private GvCriterionList.GvCriterion editSubjectRating() {
        GvCriterionList.GvCriterion child = GvDataMocker.newChild(null);
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());

        //Kind of simulate touch
        mSolo.clickOnView(mSolo.getView(R.id.review_rating));
        assertEquals(3.0f, getBuilder().getRating());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    private void clickShare() {
        mSolo.clickOnText(getActivity().getResources().getString(R
                .string.button_share));
    }

    //Overridden
    @Override
    protected void setAdapter() {
        ReviewBuilder builder =
                new ReviewBuilder(getActivity(), RandomAuthor.nextAuthor(), new TagsManager());
        mAdapter = new ReviewBuilderAdapter(builder);
        mScreen = new BuildScreen(getActivity(), (ReviewBuilderAdapter) mAdapter);
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    @Override
    protected void setUp() {
        super.setUp();

        mList = (WrapperGridData) mAdapter.getGridData();
        mOriginalSubject = mAdapter.getSubject();
        mOriginalRating = mAdapter.getRating();

        checkSubjectRating();
        checkBuilderChanges(null);

        mSignaler = new CallBackSignaler(5);
    }
}
