/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 05/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditChildrenTest extends ActivityEditScreenTest {
    private static final int AVERAGE = R.id.menu_item_average_rating;
    private float mGridRatingBeforeDone;
    private boolean mIsAverage = false;
    private float mOriginalRatingNotAverage;

    public ActivityEditChildrenTest() {
        super(GvDataList.GvType.CHILDREN);
    }

    @SmallTest
    public void testPreexistingDataShowsAvg() {
        mIsAverage = true;
        super.testPreexistingDataShows();
    }

    @SmallTest
    public void testSubjectRatingChangeAvg() {
        mIsAverage = true;
        super.testSubjectRatingChange();
    }

    @SmallTest
    public void testBannerButtonAddDoneAvg() {
        mIsAverage = true;
        super.testBannerButtonAddDone();
    }

    @SmallTest
    public void testBannerButtonAddCancelAvg() {
        mIsAverage = true;
        super.testBannerButtonAddCancel();
    }

    @SmallTest
    public void testGridItemEditDoneAvg() {
        mIsAverage = true;
        super.testGridItemEditDone();
    }

    @SmallTest
    public void testGridItemEditCancelAvg() {
        mIsAverage = true;
        super.testGridItemEditCancel();
    }

    @SmallTest
    public void testGridItemDeleteConfirmAvg() {
        mIsAverage = true;
        super.testGridItemDeleteConfirm();
    }

    @SmallTest
    public void testGridItemDeleteCancelAvg() {
        mIsAverage = true;
        super.testGridItemDeleteCancel();
    }

    @SmallTest
    public void testMenuDeleteConfirmAvg() {
        mIsAverage = true;
        super.testMenuDeleteConfirm();
    }

    @SmallTest
    public void testMenuDeleteCancelAvg() {
        mIsAverage = true;
        super.testMenuDeleteCancel();
    }

    @SmallTest
    public void testMenuUpCancelsAvg() {
        mIsAverage = true;
        super.testMenuUpCancels();
    }

    @SmallTest
    public void testMenuAverageRating() {
        setUp(true);
        float fragmentAverage = getFragmentViewReview().getRating();
        float fromChildren = getAverageRating(true);
        if (fragmentAverage == fromChildren) {
            editRating(fragmentAverage + fragmentAverage < 5f ? 0.5f : -0.5f);
        }

        assertFalse(getFragmentViewReview().getRating() == fromChildren);
        assertFalse(mController.getReviewNode().isReviewRatingAverage());

        mSolo.clickOnActionBarItem(AVERAGE);

        assertTrue(getFragmentViewReview().getRating() == fromChildren);
        assertTrue(mController.getReviewNode().isReviewRatingAverage());
    }

    @Override
    protected void enterDatum(GvDataList.GvData datum) {
        GvChildrenList.GvChildReview child = (GvChildrenList.GvChildReview) datum;
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));
    }

    @SmallTest
    public void testActivityLaunches() {
        super.testActivityLaunches();
    }

    @SmallTest
    public void testPreexistingDataShows() {
        super.testPreexistingDataShows();
    }

    @SmallTest
    public void testSubjectRatingChange() {
        super.testSubjectRatingChange();
    }

    @SmallTest
    public void testBannerButtonAddDone() {
        super.testBannerButtonAddDone();
    }

    @SmallTest
    public void testBannerButtonAddCancel() {
        super.testBannerButtonAddCancel();
    }

    @Override
    public void testGridItemEditDone() {
        super.testGridItemEditDone();
    }

    @Override
    public void testGridItemEditCancel() {
        super.testGridItemEditCancel();
    }

    @Override
    public void testGridItemDeleteConfirm() {
        super.testGridItemDeleteConfirm();
    }

    @Override
    public void testGridItemDeleteCancel() {
        super.testGridItemDeleteCancel();
    }

    @SmallTest
    public void testMenuDeleteConfirm() {
        super.testMenuDeleteConfirm();
    }

    @SmallTest
    public void testMenuDeleteCancel() {
        super.testMenuDeleteCancel();
    }

    @SmallTest
    public void testMenuUpCancels() {
        super.testMenuUpCancels();
    }

    @Override
    protected void pressDone() {
        mGridRatingBeforeDone = getAverageRating(false);
        super.pressDone();
    }

    @Override
    protected void setUpFinish(boolean withData) {
        mController.getReviewNode().setReviewRatingAverage(false);
        mOriginalRatingNotAverage = mController.getRating();
        mController.getReviewNode().setReviewRatingAverage(mIsAverage);
        mSolo.setProgressBar(0, (int) (mController.getRating() * 2f));
        super.setUpFinish(withData);
    }

    @Override
    protected void checkSubjectRating() {
        if (mController.getReviewNode().isReviewRatingAverage()) {
            checkFragmentSubjectRating(mOriginalSubject, getAverageRating(true));
            checkControllerSubjectRating();
        } else {
            super.checkSubjectRating();
        }
    }

    @Override
    protected void checkControllerSubjectRating() {
        float rating = mController.getReviewNode().isReviewRatingAverage() ? mOriginalRating
                : mOriginalRatingNotAverage;

        checkControllerSubjectRating(mOriginalSubject, rating);
    }

    @Override
    protected void checkControllerSubjectRatingOnDone() {
        if (mController.getReviewNode().isReviewRatingAverage()) {
            checkControllerSubjectRating(mOriginalSubject, mGridRatingBeforeDone);
        } else {
            super.checkControllerSubjectRatingOnDone();
        }
    }

    @Override
    protected void checkFragmentSubjectRating() {
        float rating = mController.getReviewNode().isReviewRatingAverage() ? getAverageRating(true)
                : mOriginalRating;

        checkFragmentSubjectRating(mOriginalSubject, rating);
    }

    @Override
    protected void editRating(float rating) {
        //Ensure touch event to set Review.isAverageRating() to false.
        if (mIsAverage) mSolo.clickOnView(mSolo.getView(R.id.rating_bar));

        super.editRating(rating);
    }

    private float getAverageRating(boolean nearestHalf) {
        int numCells = getGridSize();
        float rating = 0;
        for (int i = 0; i < numCells; ++i) {
            GvChildrenList.GvChildReview review = (GvChildrenList.GvChildReview) getGridItem(i);
            rating += review.getRating();
        }

        if (rating > 0) rating /= numCells;

        return nearestHalf ? Math.round(rating * 2f) / 2f : rating;
    }
}

