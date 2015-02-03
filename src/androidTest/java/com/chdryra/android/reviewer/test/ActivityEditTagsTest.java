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
    protected void enterData(GvDataList data) {
        for (int i = 0; i < data.size() - 1; ++i) {
            GvTagList.GvTag tag = (GvTagList.GvTag) data.getItem(i);
            mSolo.enterText(mSolo.getEditText(0), tag.get());
            mSolo.clickOnButton("Add");
        }

        GvTagList.GvTag tag = (GvTagList.GvTag) data.getItem(data.size() - 1);
        mSolo.enterText(mSolo.getEditText(0), tag.get());
        mSolo.clickOnButton("Done");
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
}
