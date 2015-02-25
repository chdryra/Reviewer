/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditImagesTest extends ActivityEditScreenTest {

    public ActivityEditImagesTest() {
        super(GvDataList.GvType.IMAGES);
    }


    @SmallTest
    public void testBannerButtonAddDone() {
        setUp(false);

        mSolo.clickOnButton("Add " + GvDataList.GvType.IMAGES.getDatumString());
        getInstrumentation().waitForIdleSync();

        assertTrue(mSolo.searchText("Select Source"));
        assertTrue(mSolo.searchText("Camera"));
        assertTrue(mSolo.searchText("Gallery"));
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
    }

    @Override
    public void testBannerButtonAddCancel() {

    }

    @Override
    protected GvDataList.GvData newEditDatum(GvDataList.GvData current) {
        GvImageList.GvImage oldDatum = (GvImageList.GvImage) current;
        return new GvImageList.GvImage(oldDatum.getBitmap(),
                oldDatum.getLatLng(), RandomString.nextSentence(), oldDatum.isCover());
    }
}

