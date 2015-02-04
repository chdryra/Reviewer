/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditCommentsTest extends ActivityEditScreenTest {

    public ActivityEditCommentsTest() {
        super(GvDataList.GvType.COMMENTS);
    }

    @Override
    protected void enterDatum(GvDataList.GvData datum) {
        GvCommentList.GvComment comment = (GvCommentList.GvComment) datum;
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), comment.getComment());
    }

    @SmallTest
    public void testActivityLaunches() {
        super.testActivityLaunches();
    }

    @SmallTest
    public void testSubjectRatingChange() {
        super.testSubjectRatingChange();
    }

    @SmallTest
    public void testBannerButtonAdd() {
        super.testBannerButtonAdd();
    }

    @SmallTest
    public void testPreexistingDataShows() {
        super.testPreexistingDataShows();
    }

    @Override
    public void testGridItemEdit() {
        super.testGridItemEdit();
    }

    @Override
    public void testGridItemDeleteConfirm() {
        super.testGridItemDeleteConfirm();
    }

    @Override
    public void testGridItemDeleteCancel() {
        super.testGridItemDeleteCancel();
    }
}
