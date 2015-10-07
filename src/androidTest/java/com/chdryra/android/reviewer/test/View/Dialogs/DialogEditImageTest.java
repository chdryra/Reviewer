/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditImageTest extends DialogGvDataEditTest<GvImageList.GvImage> {
    private GvImageList.GvImage mCurrent;

    //Constructors
    public DialogEditImageTest() {
        super(ConfigGvDataAddEditView.EditImage.class);
    }

    //protected methods
    @Override
    protected GvImageList.GvImage getDataShown() {
        String caption = mSolo.getEditText(0).getText().toString().trim();
        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), caption, mCurrent.isCover());

        return mCurrent;
    }

    @Override
    protected GvData getEditDatum() {
        GvImageList.GvImage child = (GvImageList.GvImage) super.getEditDatum();

        mCurrent = new GvImageList.GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), child.getCaption(), mCurrent.isCover());

        return mCurrent;
    }

    @Override
    protected GvData newDatum() {
        mCurrent = (GvImageList.GvImage) super.newDatum();
        return mCurrent;
    }

    @Override
    protected GvData launchDialogAndTestShowing() {
        mCurrent = (GvImageList.GvImage) super.launchDialogAndTestShowing();
        return mCurrent;
    }
}


