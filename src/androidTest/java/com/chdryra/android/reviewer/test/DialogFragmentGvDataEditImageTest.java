/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataEditImageTest extends DialogFragmentGvDataEditTest<GvImageList
        .GvImage> {
    private GvImageList.GvImage mCurrent;

    public DialogFragmentGvDataEditImageTest() {
        super(ConfigGvDataAddEditDisplay.EditImage.class);
    }

    @Override
    protected GvImageList.GvImage editData() {
        GvImageList.GvImage child = GvDataMocker.newImage();
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), child.getCaption());

        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getLatLng(),
                child.getCaption(), mCurrent.isCover());

        return mCurrent;
    }

    @Override
    protected GvImageList.GvImage getDataShown() {
        String caption = mSolo.getEditText(0).getText().toString().trim();
        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getLatLng(),
                caption, mCurrent.isCover());

        return mCurrent;
    }

    @SmallTest
    public void testCancelButton() {
        super.testCancelButton();
    }

    @SmallTest
    public void testDoneButton() {
        super.testDoneButton();
    }

    @SmallTest
    public void testDeleteButtonNoEditCancel() {
        super.testDeleteButtonNoEditCancel();
    }

    @SmallTest
    public void testDeleteButtonNoEditConfirm() {
        super.testDeleteButtonNoEditConfirm();
    }

    @SmallTest
    public void testDeleteButtonWithEditCancel() {
        super.testDeleteButtonWithEditCancel();
    }

    @SmallTest
    public void testDeleteButtonWithEditConfirm() {
        super.testDeleteButtonWithEditConfirm();
    }

    @Override
    protected GvImageList.GvImage launchDialogAndTestShowing() {
        mCurrent = super.launchDialogAndTestShowing();
        return mCurrent;
    }
}


