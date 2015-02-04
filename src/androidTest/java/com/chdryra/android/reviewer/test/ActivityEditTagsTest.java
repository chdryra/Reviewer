/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditTagsTest extends ActivityEditScreenTest {

    public ActivityEditTagsTest() {
        super(GvDataList.GvType.TAGS);
    }

    @Override
    protected void enterDatum(GvDataList.GvData datum) {
        GvTagList.GvTag tag = (GvTagList.GvTag) datum;
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), tag.get());
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

    @SmallTest
    public void testMenuDeleteConfirm() {
        super.testMenuDeleteConfirm();
    }

    @SmallTest
    public void testMenuDeleteCancel() {
        super.testMenuDeleteCancel();
    }
}
