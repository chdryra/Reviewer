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

    //    @SmallTest
//    public void testForDubugging() {
//        super.testActivityLaunches();
//        super.testSubjectRatingChange();
//        super.testBannerButtonAddDone();
//        super.testBannerButtonAddCancel();
//        super.testGridItemEditDone();
//        super.testGridItemEditCancel();
//        super.testGridItemDeleteConfirm();
//        super.testGridItemDeleteCancel();
//        super.testMenuDeleteConfirm();
//        super.testMenuDeleteCancel();
//        super.testMenuUpCancels();
//    }

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
        assertFalse(getBuilder().isRatingAverage());

        mSolo.clickOnActionBarItem(AVERAGE);

        assertTrue(getFragmentViewReview().getRating() == fromChildren);
        assertTrue(getBuilder().isRatingAverage());
    }

    @Override
    protected void clickMenuDone() {
        mGridRatingBeforeDone = getAverageRating(false);
        super.clickMenuDone();
    }

    @Override
    protected void setUpFinish(boolean withData) {
        getBuilder().setRatingIsAverage(false);
        mOriginalRatingNotAverage = mAdapter.getRating();
        getBuilder().setRatingIsAverage(mIsAverage);
        mSolo.setProgressBar(0, (int) (mAdapter.getRating() * 2f));
        super.setUpFinish(withData);
    }

    @Override
    protected void checkSubjectRating() {
        if (getBuilder().isRatingAverage()) {
            checkFragmentSubjectRating(mOriginalSubject, getAverageRating(true));
            checkAdapterSubjectRating();
        } else {
            super.checkSubjectRating();
        }
    }

    @Override
    protected void checkAdapterSubjectRating() {
        float rating = getBuilder().isRatingAverage() ? mOriginalRating
                : mOriginalRatingNotAverage;

        checkAdapterSubjectRating(mOriginalSubject, rating);
    }

    @Override
    protected void checkAdapterSubjectRatingOnDone() {
        if (getBuilder().isRatingAverage()) {
            checkAdapterSubjectRating(mOriginalSubject, mGridRatingBeforeDone);
        } else {
            super.checkAdapterSubjectRatingOnDone();
        }
    }

    @Override
    protected void checkFragmentSubjectRating() {
        float rating = getBuilder().isRatingAverage() ? getAverageRating(true)
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

        if (numCells > 0) rating /= numCells;

        return nearestHalf ? Math.round(rating * 2f) / 2f : rating;
    }
}

