/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 February, 2015
 */

package com.chdryra.android.reviewer.test;

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

//    @SmallTest
//    public void testForDebugging() {
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
}
