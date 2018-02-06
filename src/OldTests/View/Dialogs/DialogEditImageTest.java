/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.startouch.test.View.Dialogs;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditImageTest extends DialogGvDataEditTest<GvImage> {
    private GvImage mCurrent;

    //Constructors
    public DialogEditImageTest() {
        super(UiAndroid.DefaultLaunchables.EditImage.class);
    }

    //protected methods
    @Override
    protected GvImage getDataShown() {
        String caption = mSolo.getEditText(0).getText().toString().trim();
        mCurrent = new GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), caption, mCurrent.isCover());

        return mCurrent;
    }

    @Override
    protected GvData getEditDatum() {
        GvImage child = (GvImage) super.getEditDatum();

        mCurrent = new GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), child.getCaption(), mCurrent.isCover());

        return mCurrent;
    }

//Overridden
    @Override
    protected GvData newDatum() {
        mCurrent = (GvImage) super.newDatum();
        return mCurrent;
    }

    @Override
    protected GvData launchDialogAndTestShowing() {
        mCurrent = (GvImage) super.launchDialogAndTestShowing();
        return mCurrent;
    }
}


