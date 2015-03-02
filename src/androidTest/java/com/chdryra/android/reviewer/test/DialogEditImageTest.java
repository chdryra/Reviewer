/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditImageTest extends DialogEditGvDataTest<GvImageList
        .GvImage> {
    private GvImageList.GvImage mCurrent;

    public DialogEditImageTest() {
        super(ConfigGvDataAddEdit.EditImage.class);
    }

    @Override
    protected GvImageList.GvImage getDataShown() {
        String caption = mSolo.getEditText(0).getText().toString().trim();
        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), caption, mCurrent.isCover());

        return mCurrent;
    }

    @Override
    protected GvDataList.GvData launchDialogAndTestShowing() {
        mCurrent = (GvImageList.GvImage) super.launchDialogAndTestShowing();
        return mCurrent;
    }

    @Override
    protected GvDataList.GvData getNewDatum() {
        GvImageList.GvImage child = (GvImageList.GvImage) super.getNewDatum();

        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), child.getCaption(), mCurrent.isCover());

        return mCurrent;
    }
}


