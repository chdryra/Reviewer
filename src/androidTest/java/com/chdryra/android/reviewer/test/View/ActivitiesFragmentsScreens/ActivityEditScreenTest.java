/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.DialogAddGvData;
import com.chdryra.android.reviewer.View.Dialogs.DialogEditGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Screens.EditScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.SoloDataEntry;
import com.chdryra.android.reviewer.test.TestUtils.SoloUtils;
import com.chdryra.android.testutils.CallBackSignaler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityEditScreenTest extends ActivityReviewViewTest {
    protected static final long TIMEOUT  = 5000;
    protected static final int  DELETE   = com.chdryra.android.reviewer.R.id
            .menu_item_delete;
    protected static final int  DONE     = com.chdryra.android.reviewer.R.id
            .menu_item_done;
    protected static final int  NUM_DATA = 3;

    protected final GvDataType mDataType;
    private final Map<Button, Runnable> mClickRunnables = new HashMap<>();
    protected String                          mOriginalSubject;
    protected float                           mOriginalRating;
    protected GvDataList                      mData;
    protected CallBackSignaler                mSignaler;
    private   ConfigGvDataUi.LaunchableConfig mAddConfig;
    private   ConfigGvDataUi.LaunchableConfig mEditConfig;
    private boolean mWithData = false;

    protected enum Button {
        ADDCANCEL, ADDADD, ADDDONE, EDITCANCEL, EDITDELETE, EDITDONE,
        DELETECONFIRM, DELETECANCEL
    }

    public ActivityEditScreenTest(GvDataType dataType) {
        mDataType = dataType;
    }

    @SmallTest
    public void testSubjectRatingChange() {
        setUp(false);

        GvChildList.GvChildReview child = editSubjectRating();

        mSolo.sleep(500);

        checkFragmentSubjectRating(child.getSubject(), child.getRating());
        checkBuildersSubjectRatingAsExpected();

        clickMenuDone();

        checkBuildersSubjectRating(child.getSubject(), child.getRating());
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

        checkBuildersSubjectRatingAsExpected();
        checkInBuilder(mData, true);
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
    protected void setAdapter() {
        ReviewBuilder builder = Administrator.get(getInstrumentation().getTargetContext())
                .getReviewBuilder();
        ReviewBuilder.DataBuilder dbuilder = builder.getDataBuilder(mDataType);

        if (mWithData) {
            mData = newData();
            for (int i = 0; i < mData.size(); ++i) {
                //TODO make type safe
                dbuilder.add((GvData) mData.getItem(i));
            }
            dbuilder.setData();
        }

        mAdapter = dbuilder;
    }

    @Override
    protected ReviewView getView() {
        return EditScreen.newScreen(getInstrumentation().getTargetContext(), mDataType);
    }

    @Override
    public void testSubjectRating() {
        setUp(false);
        super.testSubjectRating();
    }

    @Override
    protected void setUp() {
    }

    protected void setUp(boolean withData) {
        Administrator.get(getInstrumentation().getTargetContext()).newReviewBuilder();
        mWithData = withData;
        super.setUp();

        mAddConfig = ConfigGvDataUi.getConfig(mDataType).getAdderConfig();
        mEditConfig = ConfigGvDataUi.getConfig(mDataType).getEditorConfig();

        SoloUtils.pretouchScreen(mActivity, mSolo);

        setUpFinish(withData);
    }

    //For adjustment in ActivityEditChildrenTest
    protected void setUpFinish(boolean withData) {
        mOriginalSubject = mAdapter.getSubject();
        mOriginalRating = mAdapter.getRating();

        checkFragmentSubjectRatingAsExpected();
        if (withData) checkInGrid(mData, true);

        setDialogButtonClickRunnables();
    }

    protected void checkInGrid(GvDataList data, boolean isInGrid) {
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

    protected void checkBuildersSubjectRatingOnDone() {
        checkBuildersSubjectRatingAsExpected();
    }

    protected void checkBuildersSubjectRatingAsExpected() {
        checkBuildersSubjectRating(mOriginalSubject, mOriginalRating);
    }

    protected void checkBuildersSubjectRating(String subject, float rating) {
        checkBuilderSubjectRating(getBuilder(), subject, rating);
        checkBuilderSubjectRating(getParentBuilder(), subject, rating);
    }

    protected void checkBuilderSubjectRating(ReviewViewAdapter builder, String subject,
            float rating) {
        assertEquals(subject, builder.getSubject());
        assertEquals(rating, builder.getRating(), 0.01);
    }

    protected void checkFragmentSubjectRatingAsExpected() {
        checkFragmentSubjectRating(mOriginalSubject, mOriginalRating);
    }

    protected void checkFragmentSubjectRating(String subject, float rating) {
        float nearestHalf = Math.round(rating * 2f) / 2f;
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(subject, fragment.getSubject());
        assertEquals(nearestHalf, fragment.getRating());
    }

    protected GvChildList.GvChildReview editSubjectRating() {
        GvChildList.GvChildReview child = GvDataMocker.newChild(null);
        editSubject(child.getSubject());
        editRating(child.getRating());
        return child;
    }

    protected void editSubject(String subject) {
        SoloDataEntry.enterSubject(mSolo, subject);
    }

    protected void editRating(float rating) {
        SoloDataEntry.enterRating(mSolo, rating);
    }

    //For ActivityEditLocationsTest
    protected GvDataList getAddDataToTestAgainst(GvDataList data) {
        return data;
    }

    protected void testGridItemEdit(boolean confirm) {
        setUp(true);

        GvDataList data = MdGvConverter.copy(getBuilder().getGridData());
        GvData currentDatum = getGridItem(0);
        GvData newDatum = newEditDatum(currentDatum);

        checkInBuilders(data, true);
        checkInGrid(data, true);

        editData(0, newDatum, confirm);

        //TODO make type safe
        if (confirm) {
            data.remove(currentDatum);
            data.add(newDatum);
        }

        checkFragmentSubjectRatingAsExpected();

        checkInGrid(data, true);
        checkInBuilder(data, true);
        checkInParentBuilder(data, !confirm);

        clickMenuDone();

        checkInParentBuilder(data, true);
        checkBuildersSubjectRatingOnDone();
    }

    protected GvData newEditDatum(GvData oldDatum) {
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

    protected void checkLaunchableShowing(boolean isShowing) {
        testDialogShowing(isShowing);
    }

    protected void editData(int position, GvData newDatum, boolean confirm) {
        launchGridItemLaunchable(position);

        SoloDataEntry.enter(mSolo, newDatum);

        if (confirm) {
            clickEditConfirm();
        } else {
            clickEditCancel();
        }

        waitForLaunchableToClose();
        checkLaunchableShowing(false);
    }

    protected void launchAdder() {
        mSolo.clickOnButton("Add " + mDataType.getDatumName());
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
            enterDatum(data, i);
            clickAddAdd();
        }

        enterDatum(data, data.size() - 1);

        if (confirm) {
            clickAddConfirm();
        } else {
            clickAddCancel();
        }
    }

    protected void enterDatum(GvDataList data, int index) {
        SoloDataEntry.enter(mSolo, (GvData) data.getItem(index));
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

    protected void checkInBuilders(GvData datum, boolean result) {
        checkInBuilder(datum, result);
        checkInParentBuilder(datum, result);
    }

    protected void checkInParentBuilder(GvData datum, boolean result) {
        //TODO make type safe
        assertEquals(result, getParentBuilder().getData(mDataType).contains(datum));
    }

    protected void checkInBuilder(GvData datum, boolean result) {
        GvDataList data = mAdapter.getGridData();
        if (result) {
            assertTrue(data.size() > 0);
        }

        boolean inBuilder = false;
        for (int i = 0; i < data.size(); ++i) {
            GvData datumController = (GvData) data.getItem(i);
            if (datumController.equals(datum)) {
                inBuilder = true;
                break;
            }
        }

        assertTrue(result ? inBuilder : !inBuilder);
    }

    protected ReviewBuilder.DataBuilder getBuilder() {
        return (ReviewBuilder.DataBuilder) mAdapter;
    }

    protected void checkInBuilders(GvDataList data, boolean result) {
        checkInBuilder(data, result);
        checkInParentBuilder(data, result);
    }

    protected void checkInParentBuilder(GvDataList data, boolean result) {
        GvDataList builderData = getParentBuilder().getData(mDataType);
        builderData.sort();
        data.sort();
        if (data.size() == 0 && builderData.size() == 0 && !result) return;

        assertEquals(result, builderData.equals(data));
    }

    protected void checkInBuilder(GvDataList data, boolean result) {
        GvDataList fromBuilder = getBuilder().getGridData();
        if (data == null) {
            assertEquals(0, fromBuilder.size());
            return;
        }

        fromBuilder.sort();
        data.sort();
        assertEquals(result, data.equals(fromBuilder));
    }

    protected void clickOnGridItem(final int position) {
        mSolo.clickInList(position + 1);
    }

    protected void clickLongOnGridItem(final int position) {
        mSolo.clickLongInList(position + 1);
    }

    protected void runOnUiThread(Runnable runnable) {
        mActivity.runOnUiThread(runnable);
    }

    protected void checkInGrid(GvData datum, boolean result) {
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

    protected ReviewBuilder getParentBuilder() {
        return getBuilder().getParentBuilder();
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
        checkInBuilders(data, false);
        checkInGrid(data, false);

        checkLaunchableShowing(false);

        launchAdder();

        waitForLaunchableToLaunch();
        checkLaunchableShowing(true);

        enterData(data, confirm);

        waitForLaunchableToClose();
        checkLaunchableShowing(false);

        checkFragmentSubjectRatingAsExpected();
        checkBuildersSubjectRatingAsExpected();

        if (!confirm) data.removeAll();
        GvDataList testData = getAddDataToTestAgainst(data);

        checkInGrid(testData, true);
        checkInBuilder(testData, true);
        checkInParentBuilder(testData, false);

        clickMenuDone();

        checkBuildersSubjectRatingOnDone();
        checkInBuilder(testData, true);
        checkInParentBuilder(testData, true);
    }

    private void testGridItemDelete(boolean confirm) {
        setUp(true);

        GvData currentDatum = getGridItem(0);

        checkInBuilders(currentDatum, true);
        checkInGrid(currentDatum, true);

        deleteGridItem(0, confirm);

        checkInGrid(currentDatum, !confirm);
        checkInBuilder(currentDatum, !confirm);
        checkInParentBuilder(currentDatum, true);

        clickMenuDone();

        checkInParentBuilder(currentDatum, !confirm);
        checkBuildersSubjectRatingOnDone();
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

        checkInGrid(mData, !confirm);
        checkInBuilder(mData, !confirm);
        checkInParentBuilder(mData, true);

        clickMenuDone();

        assertEquals(confirm ? 0 : mData.size(), getParentBuilder().getDataSize(mDataType));
        checkBuildersSubjectRatingOnDone();
    }

    private void launchGridItemLaunchable(int position) {
        checkLaunchableShowing(false);
        clickOnGridItem(position);
        waitForLaunchableToLaunch();
        checkLaunchableShowing(true);
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
        checkLaunchableShowing(false);
    }

    private void click(Button button) {
        runOnUiThread(mClickRunnables.get(button));
        mSignaler.waitForSignal();
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

    private DialogEditGvData getEditDialog() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(mEditConfig.getTag());
        return (DialogEditGvData) f;
    }

    private DialogAddGvData getAddDialog() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(mAddConfig.getTag());
        return (DialogAddGvData) f;
    }

    private DialogAlertFragment getDeleteConfirmDialog() {
        FragmentManager manager = getEditActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogDeleteConfirm.DELETE_CONFIRM_TAG);
        return (DialogAlertFragment) f;
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
}

