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
    private float mRatingBeforeDone;

    public ActivityEditChildrenTest() {
        super(GvDataList.GvType.CHILDREN);
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

    @Override
    protected void pressDone() {
        mRatingBeforeDone = getAverageRating();
        super.pressDone();
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
    protected void setUp(boolean withData) {
        super.setUp(withData, true);
    }

    @Override
    protected void checkSubjectRating() {
        if (mController.getReviewNode().isReviewRatingAverage()) {
            checkFragmentSubjectRating(mOriginalSubject, getAverageRating());
            super.checkControllerSubjectRating();
        } else {
            super.checkSubjectRating();
        }
    }

    @Override
    protected void checkControllerSubjectRatingOnDone() {
        if (mController.getReviewNode().isReviewRatingAverage()) {
            checkControllerSubjectRating(mOriginalSubject, mRatingBeforeDone);
        } else {
            super.checkControllerSubjectRatingOnDone();
        }
    }

    private float getAverageRating() {
        int numCells = getGridSize();
        float rating = 0;
        for (int i = 0; i < numCells; ++i) {
            GvChildrenList.GvChildReview review = (GvChildrenList.GvChildReview) getGridItem(i);
            rating += review.getRating();
        }

        if (rating > 0) rating /= numCells;

        return rating;
    }
}

