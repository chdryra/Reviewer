/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 February, 2015
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditFactsTest extends ActivityEditScreenTest {

    public ActivityEditFactsTest() {
        super(GvDataList.GvType.FACTS);
    }

    @Override
    protected void enterDatum(GvDataList.GvData datum) {
        GvFactList.GvFact fact = (GvFactList.GvFact) datum;
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.clearEditText(mSolo.getEditText(1));
        mSolo.enterText(mSolo.getEditText(0), fact.getLabel());
        mSolo.enterText(mSolo.getEditText(1), fact.getValue());
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

