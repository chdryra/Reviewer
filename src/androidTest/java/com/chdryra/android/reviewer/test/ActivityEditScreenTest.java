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
import android.graphics.Point;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.GridView;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.ConfigGvDataUi;
import com.chdryra.android.reviewer.DialogFragmentGvDataAdd;
import com.chdryra.android.reviewer.DialogFragmentGvDataEdit;
import com.chdryra.android.reviewer.FragmentViewReview;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.ReviewAdapter;
import com.chdryra.android.reviewer.ReviewBuilder;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.chdryra.android.testutils.CallBackSignaler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityEditScreenTest extends ActivityViewReviewTest {
    protected static final long                TIMEOUT  = 5000;
    protected static final int                 DELETE   = com.chdryra.android.reviewer.R.id
            .menu_item_delete;
    protected static final int                 DONE     = com.chdryra.android.reviewer.R.id
            .menu_item_done;
    private static final   int                 NUM_DATA = 3;
    private static final   GvDataList.GvType[] TYPES    = {GvDataList.GvType.COMMENTS, GvDataList
            .GvType.TAGS, GvDataList.GvType.LOCATIONS, GvDataList.GvType.URLS,
            GvDataList.GvType.CHILDREN, GvDataList.GvType.FACTS, GvDataList.GvType.IMAGES};

    protected String                          mOriginalSubject;
    protected float                           mOriginalRating;
    private   GvDataList                      mData;
    private   ConfigGvDataUi.LaunchableConfig mAddConfig;
    private   ConfigGvDataUi.LaunchableConfig mEditConfig;
    private   CallBackSignaler                mSignaler;
    private Map<Button, Runnable> mClickRunnables = new HashMap<>();

    private boolean mWithData = false;

    protected enum Button {
        ADDCANCEL, ADDADD, ADDDONE, EDITCANCEL, EDITDELETE, EDITDONE,
        DELETECONFIRM, DELETECANCEL
    }

    public ActivityEditScreenTest(GvDataList.GvType dataType) {
        super(dataType, true);
    }


    @SmallTest
    public void testSubjectRatingChange() {
        setUp(false);

        GvChildrenList.GvChildReview child = editSubjectRating();

        mSolo.sleep(1000);

        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkControllerSubjectRating();

        clickMenuDone();

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
        editData(0, newEditDatum(getGridItem(0)), true);
        deleteGridItem(1, true);

        clickMenuUp();

        checkControllerSubjectRating();
        checkControllerDataChanges();
    }

    protected void clickMenuDelete() {
        mSolo.clickOnActionBarItem(DELETE);
    }

    protected void clickMenuDone() {
        mSolo.clickOnActionBarItem(DONE);
    }

    protected void clickMenuUp() {
        mSolo.clickOnActionBarHomeButton();
    }

    @Override
    protected ReviewAdapter getController() {
        ReviewBuilder controller = new ReviewBuilder(getInstrumentation()
                .getTargetContext());
        if (mWithData) {
            mData = GvDataMocker.getData(mDataType, NUM_DATA);
            controller.setData(mData);
        }

        return controller;
    }

    @Override
    public void testSubjectRating() {
        setUp(false);
        super.testSubjectRating();
    }

    @Override
    public void testActivityLaunches() {
        setUp(false);
        super.testActivityLaunches();
    }

    @Override
    protected void setUp() {
    }

    protected void setUp(boolean withData) {
        mWithData = withData;
        super.setUp();

        mAddConfig = ConfigGvDataUi.getConfig(mDataType).getAdderConfig();
        mEditConfig = ConfigGvDataUi.getConfig(mDataType).getEditorConfig();

        //To avoid issues caused by spurious menu touch events in starting activity for test.
        Point displaySize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(displaySize);
        mSolo.clickLongOnScreen(displaySize.x, displaySize.y);

        setUpFinish(withData);
    }

    //For adjustment in ActivityEditChildrenTest
    protected void setUpFinish(boolean withData) {
        mOriginalSubject = mController.getSubject();
        mOriginalRating = mController.getRating();
        checkSubjectRating();

        checkControllerDataChanges();
        if (withData) testInGrid(mData, true);

        setDialogButtonClickRunnables();
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

    protected GvChildrenList.GvChildReview editSubjectRating() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();

        editSubject(child.getSubject());
        editRating(child.getRating());

        return child;
    }

    protected void editSubject(String subject) {
        mSolo.clearEditText(0);
        mSolo.enterText(mSolo.getEditText(0), subject);
    }

    protected void editRating(float rating) {
        mSolo.setProgressBar(0, (int) (rating * 2f));
    }

    //For ActivityEditLocationsTest
    protected GvDataList getAddDataToTestAgainst(GvDataList data) {
        return data;
    }

    protected void testGridItemEdit(boolean confirm) {
        setUp(true);

        GvDataList data = mController.getData(mDataType);
        GvDataList.GvData currentDatum = getGridItem(0);
        GvDataList.GvData newDatum = newEditDatum(currentDatum);

        testInController(currentDatum, true);
        testInGrid(currentDatum, true);

        editData(0, newDatum, confirm);

        if (confirm) {
            data.remove(currentDatum);
            data.add(newDatum);
        }

        testInGrid(data, true);
        checkSubjectRating();

        clickMenuDone();

        testInController(currentDatum, !confirm);
        testInController(newDatum, confirm);
        checkControllerSubjectRatingOnDone();
        checkControllerDataChanges(data);
    }

    protected GvDataList.GvData newEditDatum(GvDataList.GvData oldDatum) {
        return GvDataMocker.getDatum(mDataType);
    }

    protected GvDataList newData() {
        return GvDataMocker.getData(mDataType, NUM_DATA);
    }

    protected void waitForLaunchableToLaunch() {
        mSolo.waitForDialogToOpen(TIMEOUT);
        mSolo.sleep(1000); //need to do this due to UI thread is separate to test thread
    }

    protected void waitForLaunchableToClose() {
        mSolo.waitForDialogToClose(TIMEOUT);
        mSolo.sleep(1000);
    }

    protected void testLaunchableShowing(boolean isShowing) {
        testDialogShowing(isShowing);
    }

    protected void editData(int position, GvDataList.GvData newDatum, boolean confirm) {
        launchGridItemLaunchable(position);

        SoloDataEntry.enter(mSolo, newDatum);

        if (confirm) {
            clickEditConfirm();
        } else {
            clickEditCancel();
        }

        waitForLaunchableToClose();
        testLaunchableShowing(false);
    }

    protected void launchAdder() {
        mSolo.clickOnButton("Add " + mDataType.getDatumString());
    }

    protected void clickEditConfirm() {
        click(Button.EDITDONE);
    }

    protected void clickEditDelete() {
        click(Button.EDITDELETE);
    }

    protected void clickEditCancel() {
        click(Button.EDITCANCEL);
    }

    protected void clickAddConfirm() {
        click(Button.ADDDONE);
    }

    protected void clickAddAdd() {
        click(Button.ADDADD);
    }

    protected void clickAddCancel() {
        click(Button.ADDCANCEL);
    }

    protected void clickDeleteConfirm() {
        click(Button.DELETECONFIRM);
    }

    protected void clickDeleteCancel() {
        click(Button.DELETECANCEL);
    }

    protected void enterData(GvDataList data, boolean confirm) {
        for (int i = 0; i < data.size() - 1; ++i) {
            SoloDataEntry.enter(mSolo, (GvDataList.GvData) data.getItem(i));
            clickAddAdd();
        }

        SoloDataEntry.enter(mSolo, ((GvDataList.GvData) data.getItem(data.size() - 1)));

        if (confirm) {
            clickAddConfirm();
        } else {
            clickAddCancel();
        }
    }

    protected void setDialogButtonClickRunnables() {
        mSignaler = new CallBackSignaler(5);

        setAdderDialogButtonClicks();
        setEditorDialogButtonClicks();
        setDeleteConfirmButtonClicks();
    }

    protected Activity getEditActivity() {
        return mActivity;
    }

    protected void testInController(GvDataList.GvData datum, boolean result) {
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

    protected void checkControllerDataChanges() {
        if (mData != null) {
            checkControllerDataChanges(mData);
        } else {
            checkControllerDataChanges(false);
        }

    }

    protected ReviewBuilder getBuilder() {
        return (ReviewBuilder) getController();
    }

    private void testConfirmDialogShowing(boolean isShowing) {
        if (isShowing) {
            assertTrue(mSolo.searchButton("Yes"));
        } else {
            assertFalse(mSolo.searchButton("Yes"));
        }
    }

    private void testBannerButtonAdd(boolean confirm) {
        setUp(false);

        final GvDataList data = newData();
        testInController(data, false);
        testInGrid(data, false);

        testLaunchableShowing(false);

        launchAdder();

        waitForLaunchableToLaunch();
        testLaunchableShowing(true);

        enterData(data, confirm);

        waitForLaunchableToClose();
        testLaunchableShowing(false);

        //TODO make type safe
        if (!confirm) {
            data.remove(data.getItem(data.size() - 1));
        }

        GvDataList testData = getAddDataToTestAgainst(data);
        testInGrid(testData, true);
        checkSubjectRating();

        clickMenuDone();

        checkControllerSubjectRatingOnDone();
        checkControllerDataChanges(testData);
    }

    private void testGridItemDelete(boolean confirm) {
        setUp(true);

        GvDataList.GvData currentDatum = getGridItem(0);

        testInController(currentDatum, true);
        testInGrid(currentDatum, true);

        deleteGridItem(0, confirm);

        testInGrid(currentDatum, !confirm);
        checkSubjectRating();

        clickMenuDone();

        testInController(currentDatum, !confirm);
        checkControllerSubjectRatingOnDone();
        checkControllerDataChanges(true);
    }

    private void testMenuDelete(boolean confirm) {
        setUp(true);

        testConfirmDialogShowing(false);

        clickMenuDelete();

        mSolo.waitForDialogToOpen(TIMEOUT);
        testConfirmDialogShowing(true);

        if (confirm) {
            clickDeleteConfirm();
        } else {
            clickDeleteCancel();
        }

        mSolo.waitForDialogToClose(TIMEOUT);
        testConfirmDialogShowing(false);

        assertEquals(confirm ? 0 : mData.size(), getGridSize());

        testInGrid(mData, !confirm);
        testInController(mData, true);

        clickMenuDone();

        assertEquals(confirm ? 0 : mData.size(), mController.getData(mDataType).size());
        checkControllerSubjectRatingOnDone();
        if (!confirm) checkControllerDataChanges();
    }

    private void launchGridItemLaunchable(int position) {
        testLaunchableShowing(false);
        clickOnGridItem(position);
        waitForLaunchableToLaunch();
        testLaunchableShowing(true);
    }

    private void deleteGridItem(int position, boolean confirm) {
        launchGridItemLaunchable(position);

        testConfirmDialogShowing(false);
        clickEditDelete();
        mSolo.waitForDialogToOpen();
        testConfirmDialogShowing(true);

        if (confirm) {
            clickDeleteConfirm();
        } else {
            clickDeleteCancel();
            mSolo.waitForDialogToClose();
            testConfirmDialogShowing(false);
            clickEditConfirm();
        }

        waitForLaunchableToClose();
        testLaunchableShowing(false);
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
        mSignaler.waitForSignal();
    }

    private void runOnUiThread(Runnable runnable) {
        mActivity.runOnUiThread(runnable);
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
        FragmentManager manager = getEditActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogDeleteConfirm.DELETE_CONFIRM_TAG);
        return (DialogAlertFragment) f;
    }

    private void testInController(GvDataList data, boolean inController) {
        GvDataList fromController = mController.getData(mDataType);
        fromController.sort();
        data.sort();
        assertEquals(inController, data.equals(fromController));
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

    private void checkControllerDataChanges(boolean ignoreDataType) {
        for (GvDataList.GvType type : TYPES) {
            if (ignoreDataType && type == mDataType) continue;
            assertEquals(0, mController.getData(type).size());
        }
    }
}

