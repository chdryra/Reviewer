/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Point;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Display;
import android.widget.GridView;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ConfigGvDataUi;
import com.chdryra.android.reviewer.ControllerReviewTreeEditable;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.DialogFragmentGvDataEdit;
import com.chdryra.android.reviewer.FragmentViewReview;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.CallBackSignaler;
import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityEditScreenTest extends
        ActivityInstrumentationTestCase2<ActivityViewReview> {
    protected static final long                TIMEOUT  = 5000;
    protected static final int                 DELETE   = com.chdryra.android.reviewer.R.id
            .menu_item_delete;
    protected static final int                 DONE     = com.chdryra.android.reviewer.R.id
            .menu_item_done;
    private static final   int                 NUM_DATA = 3;
    private static final   GvDataList.GvType[] TYPES    = {GvDataList.GvType.COMMENTS, GvDataList
            .GvType.TAGS, GvDataList.GvType.LOCATIONS, GvDataList.GvType.URLS,
            GvDataList.GvType.CHILDREN, GvDataList.GvType.FACTS, GvDataList.GvType.IMAGES};

    protected Solo                            mSolo;
    protected ControllerReviewTreeEditable mController;
    protected GvDataList.GvType            mDataType;
    protected String                       mOriginalSubject;
    protected float                        mOriginalRating;
    private   GvDataList                   mData;
    private   ConfigGvDataUi.LaunchableConfig mAddConfig;
    private   ConfigGvDataUi.LaunchableConfig mEditConfig;
    private   Activity                        mActivity;
    private   CallBackSignaler             mSignaler;
    private Map<Button, Runnable> mClickRunnables = new HashMap<>();

    protected enum Button {
        ADDCANCEL, ADDADD, ADDDONE, EDITCANCEL, EDITDELETE, EDITDONE,
        DELETECONFIRM, DELETECANCEL
    }

    protected abstract void enterDatum(GvDataList.GvData datum);

    public ActivityEditScreenTest(GvDataList.GvType dataType) {
        super(ActivityViewReview.class);
        mDataType = dataType;
    }

    @SmallTest
    public void testActivityLaunches() {
        setUp(false);
        assertTrue(mSolo.searchText(mDataType.getDatumString()));
        mSolo.searchText(mDataType.getDataString());
    }

    @SmallTest
    public void testPreexistingDataShows() {
        setUp(true);
        testInGrid(mController.getData(mDataType), true);
    }

    @SmallTest
    public void testSubjectRatingChange() {
        setUp(false);

        GvChildrenList.GvChildReview child = editSubjectRating();

        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkControllerSubjectRating();

        pressDone();
        checkControllerSubjectRating(child.getSubject(), child.getRating());
        checkControllerDataChanges();
    }

    @SmallTest
    public void testBannerButtonAddDone() {
        testBannerButtonAdd(true);
    }

    @SmallTest
    public void testBannerButtonAddCancel() {
        testBannerButtonAdd(false);
    }

    @SmallTest
    public void testGridItemEditDone() {
        testGridItemEdit(true);
    }

    @SmallTest
    public void testGridItemEditCancel() {
        testGridItemEdit(false);
    }

    @SmallTest
    public void testGridItemDeleteConfirm() {
        testGridItemDelete(true);
    }

    @SmallTest
    public void testGridItemDeleteCancel() {
        testGridItemDelete(false);
    }

    @SmallTest
    public void testMenuDeleteConfirm() {
        testMenuDelete(true);
    }

    @SmallTest
    public void testMenuDeleteCancel() {
        testMenuDelete(false);
    }

    @SmallTest
    public void testMenuUpCancels() {
        setUp(true);

        //Edit
        editSubjectRating();
        editData(0, GvDataMocker.getDatum(mDataType), true);
        deleteGridItem(1, true);

        mSolo.clickOnActionBarHomeButton();

        checkControllerSubjectRating();
        checkControllerDataChanges();
    }

    protected void pressDone() {
        mSolo.clickOnActionBarItem(DONE);
    }

    protected GvDataList.GvData getGridItem(int position) {
        return (GvDataList.GvData) getGridView().getItemAtPosition(position);
    }

    protected void setUp(boolean withData, boolean isChildrenTest) {
        getInstrumentation().setInTouchMode(false);

        Intent i = new Intent();
        ActivityViewReview.packParameters(mDataType, true, i);

        mController = new ControllerReviewTreeEditable(ReviewMocker.newReviewTreeEditable());
        if (withData) {
            mData = GvDataMocker.getData(mDataType, NUM_DATA);
            mController.setData(mData);
        }

        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        admin.pack(mController, i);

        setActivityIntent(i);
        mActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mActivity);

        mAddConfig = ConfigGvDataUi.getConfig(mDataType).getAdderConfig();
        mEditConfig = ConfigGvDataUi.getConfig(mDataType).getEditorConfig();

        //To avoid issues caused by spurious touch events in starting activity for test...
        clickOnScreen();

        //Spurious touch events cause rating to not be properly set.
        if (isChildrenTest) doChildrenTestShenanigans();

        mOriginalSubject = mController.getSubject();
        mOriginalRating = mController.getRating();
        checkSubjectRating();

        checkControllerDataChanges();
        if (withData) testInGrid(mData, true);

        setButtonClickRunnables();
    }

    protected void clickOnScreen() {
        Display mdisp = mActivity.getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int x = mdispSize.x / 2;
        int y = mdispSize.y / 2;
        mSolo.clickLongOnScreen(x, y);
    }

    protected void setUp(boolean withData) {
        setUp(withData, false);
    }

    protected FragmentViewReview getFragmentViewReview() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentById(ActivityViewReview.FRAGMENT_ID);
        return (FragmentViewReview) f;
    }

    protected void testConfirmDialogShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchButton("Yes"));
        } else {
            assertFalse(mSolo.searchButton("Yes"));
        }
    }

    protected void testInGrid(GvDataList data, boolean isInGrid) {
        if (isInGrid) {
            assertEquals(data.size(), getGridSize());
            data.sort();
            for (int i = 0; i < data.size(); ++i) {
                assertEquals(data.getItem(i), getGridItem(i));
            }
        } else {
            boolean diff = data.size() != getGridSize();
            if (!diff) {
                for (int i = 0; i < data.size(); ++i) {
                    if (!data.getItem(i).equals(getGridItem(i))) {
                        diff = true;
                        break;
                    }
                }
            }
            assertTrue(diff);
        }


    }

    protected int getGridSize() {
        return getGridView().getAdapter().getCount();
    }

    protected GridView getGridView() {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        return (GridView) views.get(0);
    }

    protected void checkControllerDataChanges(GvDataList data) {
        testInController(data, true);
        checkControllerDataChanges(true);
    }

    protected void checkSubjectRating() {
        checkControllerSubjectRating();
        checkFragmentSubjectRating();
    }

    protected void checkControllerSubjectRating() {
        checkControllerSubjectRating(mOriginalSubject, mOriginalRating);
    }

    protected void checkControllerSubjectRatingOnDone() {
        checkControllerSubjectRating();
    }

    protected void checkFragmentSubjectRating() {
        checkFragmentSubjectRating(mOriginalSubject, mOriginalRating);
    }

    protected void checkControllerSubjectRating(String subject, float rating) {
        assertEquals(subject, mController.getSubject());
        assertEquals(rating, mController.getRating());
    }

    protected void checkFragmentSubjectRating(String subject, float rating) {
        FragmentViewReview fragment = getFragmentViewReview();
        assertEquals(subject, fragment.getSubject());
        assertEquals(rating, fragment.getRating());
    }

    private void testBannerButtonAdd(boolean confirm) {
        setUp(false);

        final GvDataList data = GvDataMocker.getData(mDataType, NUM_DATA);
        testInController(data, false);
        testInGrid(data, false);

        testDialogShowing(false);
        mSolo.clickOnButton("Add " + mDataType.getDatumString());
        mSolo.waitForDialogToOpen(TIMEOUT);
        testDialogShowing(true);

        enterData(data, confirm);

        mSolo.waitForDialogToClose();
        testDialogShowing(false);

        //TODO make type safe
        if (!confirm) {
            data.remove(data.getItem(data.size() - 1));
        }

        testInGrid(data, true);
        checkSubjectRating();

        pressDone();
        checkControllerSubjectRatingOnDone();
        checkControllerDataChanges(data);
    }

    private void testGridItemEdit(boolean confirm) {
        setUp(true);

        GvDataList data = mController.getData(mDataType);
        GvDataList.GvData currentDatum = getGridItem(0);
        GvDataList.GvData newDatum = GvDataMocker.getDatum(mDataType);

        testInController(currentDatum, true);
        testInGrid(currentDatum, true);

        editData(0, newDatum, confirm);

        if (confirm) {
            data.remove(currentDatum);
            data.add(newDatum);
        }

        testInGrid(data, true);
        checkSubjectRating();

        pressDone();
        testInController(currentDatum, !confirm);
        testInController(newDatum, confirm);
        checkControllerSubjectRatingOnDone();
        checkControllerDataChanges(data);
    }

    private void testGridItemDelete(boolean confirm) {
        setUp(true);

        GvDataList.GvData currentDatum = getGridItem(0);

        testInController(currentDatum, true);
        testInGrid(currentDatum, true);

        deleteGridItem(0, confirm);

        testInGrid(currentDatum, !confirm);
        checkSubjectRating();

        pressDone();
        testInController(currentDatum, !confirm);
        checkControllerSubjectRatingOnDone();
        checkControllerDataChanges(true);
    }

    private void testMenuDelete(boolean confirm) {
        setUp(true);

        testConfirmDialogShowing(false);
        mSolo.clickOnActionBarItem(DELETE);
        mSolo.waitForDialogToOpen(TIMEOUT);
        testConfirmDialogShowing(true);

        if (confirm) {
            click(Button.DELETECONFIRM);
        } else {
            click(Button.DELETECANCEL);
        }

        mSolo.waitForDialogToClose(TIMEOUT);
        testConfirmDialogShowing(false);

        assertEquals(confirm ? 0 : mData.size(), getGridSize());

        testInGrid(mData, !confirm);
        testInController(mData, true);

        pressDone();
        assertEquals(confirm ? 0 : mData.size(), mController.getData(mDataType).size());
        checkControllerSubjectRatingOnDone();
        if (!confirm) checkControllerDataChanges();
    }

    private void editData(int position, GvDataList.GvData newDatum, boolean confirm) {
        testInController(newDatum, false);
        testInGrid(newDatum, false);
        testDialogShowing(false);

        getGridView().getItemAtPosition(position);
        clickOnGridItem(position);
        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000); //need to do this due to UI thread is separate to test thread
        testDialogShowing(true);

        enterDatum(newDatum);

        if (confirm) {
            click(Button.EDITDONE);
        } else {
            click(Button.EDITCANCEL);
        }

        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);
        testDialogShowing(false);
    }

    private GvChildrenList.GvChildReview editSubjectRating() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();

        mSolo.clearEditText(0);
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    private void deleteGridItem(int position, boolean confirm) {
        testDialogShowing(false);
        testConfirmDialogShowing(false);
        getGridView().getItemAtPosition(position);

        clickOnGridItem(position);

        mSolo.waitForDialogToOpen();
        testDialogShowing(true);
        testConfirmDialogShowing(false);

        click(Button.EDITDELETE);

        mSolo.waitForDialogToOpen();
        testConfirmDialogShowing(true);

        if (confirm) {
            click(Button.DELETECONFIRM);
        } else {
            click(Button.DELETECANCEL);
            mSolo.waitForDialogToClose();
            testConfirmDialogShowing(false);
            click(Button.EDITDONE);
        }

        mSolo.waitForDialogToClose();
        testDialogShowing(false);
        testConfirmDialogShowing(false);
    }

    private void enterData(GvDataList data, boolean confirm) {
        for (int i = 0; i < data.size() - 1; ++i) {
            enterDatum((GvDataList.GvData) data.getItem(i));
            click(Button.ADDADD);
        }

        enterDatum((GvDataList.GvData) data.getItem(data.size() - 1));
        if (confirm) {
            click(Button.ADDDONE);
        } else {
            click(Button.ADDCANCEL);
        }
    }

    private void clickOnGridItem(final int position) {
        final GridView grid = getGridView();
        Runnable gridItemClick = new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                grid.setSelection(position);
                grid.performItemClick(grid.getSelectedView(), position,
                        grid.getItemIdAtPosition(position));
                mSignaler.signal();
            }
        };
        runOnUiThread(gridItemClick);
    }

    private void click(Button button) {
        runOnUiThread(mClickRunnables.get(button));
    }

    private void runOnUiThread(Runnable runnable) {
        mActivity.runOnUiThread(runnable);
    }

    private void doChildrenTestShenanigans() {
        mController.getReviewNode().setReviewRatingAverage(false);
        mSolo.setProgressBar(0, (int) (mController.getRating() * 2f));
    }

    private void setButtonClickRunnables() {
        mSignaler = new CallBackSignaler(5);

        setAdderDialogButtonClicks();
        setEditorDialogButtonClicks();
        setDeleteConfirmButtonClicks();
    }

    private void setAdderDialogButtonClicks() {
        mClickRunnables.put(Button.ADDCANCEL, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getAddDialog().clickCancelButton();
                mSignaler.signal();
            }
        });

        mClickRunnables.put(Button.ADDADD, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getAddDialog().clickAddButton();
                mSignaler.signal();
            }
        });

        mClickRunnables.put(Button.ADDDONE, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getAddDialog().clickDoneButton();
                mSignaler.signal();
            }
        });
    }

    private void setEditorDialogButtonClicks() {
        mClickRunnables.put(Button.EDITCANCEL, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getEditDialog().clickCancelButton();
                mSignaler.signal();
            }
        });

        mClickRunnables.put(Button.EDITDELETE, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getEditDialog().clickDeleteButton();
                mSignaler.signal();
            }
        });

        mClickRunnables.put(Button.EDITDONE, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getEditDialog().clickDoneButton();
                mSignaler.signal();
            }
        });
    }

    private void setDeleteConfirmButtonClicks() {
        mClickRunnables.put(Button.DELETECANCEL, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getDeleteConfirmDialog().clickNegativeButton();
                mSignaler.signal();
            }
        });

        mClickRunnables.put(Button.DELETECONFIRM, new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getDeleteConfirmDialog().clickPositiveButton();
                mSignaler.signal();
            }
        });
    }

    private DialogFragmentGvDataEdit getEditDialog() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(mEditConfig.getTag());
        return (DialogFragmentGvDataEdit) f;
    }

    private DialogFragmentGvDataAdd getAddDialog() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(mAddConfig.getTag());
        return (DialogFragmentGvDataAdd) f;
    }

    private DialogAlertFragment getDeleteConfirmDialog() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogDeleteConfirm.DELETE_CONFIRM_TAG);
        return (DialogAlertFragment) f;
    }

    private void testInController(GvDataList data, boolean inController) {
        GvDataList fromController = mController.getData(mDataType);
        fromController.sort();
        data.sort();
        assertEquals(inController, data.equals(fromController));
    }

    private void testInController(GvDataList.GvData datum, boolean result) {
        GvDataList data = mController.getData(mDataType);
        if (result) {
            assertTrue(data.size() > 0);
        }

        boolean inController = false;
        for (int i = 0; i < data.size(); ++i) {
            GvDataList.GvData datumController = (GvDataList.GvData) data.getItem(i);
            if (datumController.equals(datum)) {
                inController = true;
                break;
            }
        }

        assertTrue(result ? inController : !inController);
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

    private void testInGrid(GvDataList.GvData datum, boolean result) {
        if (result) {
            assertTrue(getGridSize() > 0);
        }

        boolean inGrid = false;
        for (int i = 0; i < getGridSize(); ++i) {
            if (getGridItem(i).equals(datum)) {
                inGrid = true;
                break;
            }
        }

        assertTrue(result ? inGrid : !inGrid);
    }

    private void checkControllerDataChanges() {
        if (mData != null) {
            checkControllerDataChanges(mData);
        } else {
            checkControllerDataChanges(false);
        }

    }

    private void checkControllerDataChanges(boolean ignoreDataType) {
        for (GvDataList.GvType type : TYPES) {
            if (ignoreDataType && type == mDataType) continue;
            assertEquals(0, mController.getData(type).size());
        }
    }
}

