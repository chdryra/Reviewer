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
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.GridView;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.ActivityViewReview;
import com.chdryra.android.reviewer.Administrator;
import com.chdryra.android.reviewer.ConfigGvDataUi;
import com.chdryra.android.reviewer.ControllerReviewTreeEditable;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.DialogFragmentGvDataEdit;
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
    private static final int  NUM_DATA = 3;
    private static final long TIMEOUT  = 5000;
    private static final int  DELETE   = com.chdryra.android.reviewer.R.id
            .menu_item_delete;
    private static final int  DONE     = com.chdryra.android.reviewer.R.id
            .menu_item_done;
    private static final GvDataList.GvType[] TYPES = {GvDataList.GvType.COMMENTS, GvDataList
            .GvType.TAGS, GvDataList.GvType.LOCATIONS, GvDataList.GvType.URLS,
            GvDataList.GvType.CHILDREN, GvDataList.GvType.FACTS, GvDataList.GvType.IMAGES};

    protected Solo                            mSolo;
    private   ConfigGvDataUi.LaunchableConfig mAddConfig;
    private   ConfigGvDataUi.LaunchableConfig mEditConfig;
    private   Activity                        mActivity;
    private   GvDataList.GvType               mDataType;
    private   ControllerReviewTreeEditable    mController;

    private CallBackSignaler mSignaler;
    private Map<Button, Runnable> mClickRunnables = new HashMap<>();

    private enum Button {
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
    public void testSubjectRatingChange() {
        setUp(false);
        String reviewSubject = mController.getSubject();
        String currentSubject = mSolo.getEditText(0).getText().toString();
        assertEquals(reviewSubject, currentSubject);

        GvChildrenList.GvChildReview child = editSubjectRating();

        mSolo.clickOnActionBarItem(DONE);

        assertEquals(mController.getSubject(), child.getSubject());
        assertEquals(mController.getRating(), child.getRating());
        checkControllerDataChanges();
    }

    @SmallTest
    public void testBannerButtonAdd() {
        setUp(false);
        final GvDataList data = GvDataMocker.getData(mDataType, NUM_DATA);
        testInController(data, false);

        testInGrid(data, false);

        testDialogShowing(false);
        mSolo.clickOnButton("Add " + mDataType.getDatumString());

        mSolo.waitForDialogToOpen(TIMEOUT);
        testDialogShowing(true);

        enterData(data);

        mSolo.waitForDialogToClose();
        testDialogShowing(false);

        testInGrid(data, true);

        mSolo.clickOnActionBarItem(DONE);
        checkControllerDataChanges(data, true);
    }

    @SmallTest
    public void testPreexistingDataShows() {
        setUp(true);
        testInGrid(mController.getData(mDataType), true);
    }

    @SmallTest
    public void testGridItemEdit() {
        setUp(true);

        GvDataList.GvData currentDatum = getGridItem(0);
        GvDataList.GvData newDatum = GvDataMocker.getDatum(mDataType);

        testInController(currentDatum, true);
        testInGrid(currentDatum, true);

        testInController(newDatum, false);
        testInGrid(newDatum, false);
        testDialogShowing(false);

        getGridView().getItemAtPosition(0);
        clickOnGridItem(0);
        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000);
        testDialogShowing(true);

        enterDatum(newDatum);

        click(Button.EDITDONE);
        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);
        testDialogShowing(false);

        testInGrid(currentDatum, false);
        testInGrid(newDatum, true);
        mSolo.clickOnActionBarItem(DONE);
        testInController(currentDatum, false);
        testInController(newDatum, true);

        checkControllerDataChanges(mDataType);
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
        String subject = mController.getSubject();
        float rating = mController.getRating();
        GvDataList data = mController.getData(mDataType);


    }

    private GvChildrenList.GvChildReview editSubjectRating() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();

        mSolo.clearEditText(0);
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    private void testMenuDelete(boolean confirm) {
        setUp(true);
        GvDataList data = mController.getData(mDataType);

        assertTrue(data.size() > 0);
        testInGrid(data, true);
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

        assertEquals(confirm ? 0 : data.size(), getGridView().getAdapter().getCount());

        testInGrid(data, !confirm);
        testInController(data, true);
        mSolo.clickOnActionBarItem(DONE);
        assertEquals(confirm ? 0 : data.size(), mController.getData(mDataType).size());
        checkControllerDataChanges(data, !confirm);
    }

    private void testGridItemDelete(boolean confirm) {
        setUp(true);

        GvDataList.GvData currentDatum = getGridItem(0);

        testInController(currentDatum, true);
        testInGrid(currentDatum, true);
        testDialogShowing(false);
        testConfirmDialogShowing(false);

        getGridView().getItemAtPosition(0);
        clickOnGridItem(0);
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

        testInGrid(currentDatum, !confirm);
        mSolo.clickOnActionBarItem(DONE);
        testInController(currentDatum, !confirm);
        checkControllerDataChanges(mDataType);
    }

    private GvDataList.GvData getGridItem(int position) {
        return (GvDataList.GvData) getGridView().getItemAtPosition(position);
    }

    private void enterData(GvDataList data) {
        for (int i = 0; i < data.size() - 1; ++i) {
            enterDatum((GvDataList.GvData) data.getItem(i));
            click(Button.ADDADD);
        }

        enterDatum((GvDataList.GvData) data.getItem(data.size() - 1));
        click(Button.ADDDONE);
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

    private void setUp(boolean withData) {
        getInstrumentation().setInTouchMode(false);

        Intent i = new Intent();
        ActivityViewReview.packParameters(mDataType, true, i);
        mController = new ControllerReviewTreeEditable(ReviewMocker.newReviewTreeEditable());
        if (withData) {
            GvDataList data = GvDataMocker.getData(mDataType, NUM_DATA);
            mController.setData(data);
        }
        Administrator admin = Administrator.get(getInstrumentation().getTargetContext());
        admin.pack(mController, i);

        setActivityIntent(i);
        mActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mActivity);

        mAddConfig = ConfigGvDataUi.getConfig(mDataType).getAdderConfig();
        mEditConfig = ConfigGvDataUi.getConfig(mDataType).getEditorConfig();

        setButtonClickRunnables();

        if (withData) {
            checkControllerDataChanges(mDataType);
        } else {
            checkControllerDataChanges();
        }
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

    private void testConfirmDialogShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchButton("Yes"));
        } else {
            assertFalse(mSolo.searchButton("Yes"));
        }
    }

    private void testInGrid(GvDataList data, boolean isInGrid) {
        GridView gridView = getGridView();
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

    private void testInGrid(GvDataList.GvData datum, boolean result) {
        GridView gridView = getGridView();
        if (result) {
            assertTrue(gridView.getAdapter().getCount() > 0);
        }

        boolean inGrid = false;
        for (int i = 0; i < gridView.getAdapter().getCount(); ++i) {
            GvDataList.GvData datumGrid = (GvDataList.GvData) gridView.getAdapter().getItem(i);
            if (datumGrid.equals(datum)) {
                inGrid = true;
                break;
            }
        }

        assertTrue(result ? inGrid : !inGrid);
    }

    private GridView getGridView() {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        return (GridView) views.get(0);
    }

    private void checkControllerDataChanges(GvDataList data, boolean inController) {
        testInController(data, inController);
        checkControllerDataChanges(data.getGvType());
    }

    private void checkControllerDataChanges() {
        checkControllerDataChanges(null);
    }

    private void checkControllerDataChanges(GvDataList.GvType toIgnore) {
        for (GvDataList.GvType type : TYPES) {
            if (type == toIgnore) continue;
            assertEquals(0, mController.getData(type).size());
        }
    }
}

