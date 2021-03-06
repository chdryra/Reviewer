/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 March, 2015
 */

package com.chdryra.android.startouch.test.View.Dialogs;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditLocationTest extends DialogGvDataEditTest<GvLocation> {
    private GvLocation mCurrent;

    //Constructors
    public DialogEditLocationTest() {
        super(UiAndroid.DefaultLaunchables.EditLocation.class);
    }

    //protected methods
    @Override
    protected GvLocation getDataShown() {
        String name = mSolo.getEditText(0).getText().toString().trim();
        mCurrent = new GvLocation(mCurrent.getLatLng(), name);

        return mCurrent;
    }

    @Override
    protected GvData getEditDatum() {
        GvLocation child = (GvLocation) super.getEditDatum();

        mCurrent = new GvLocation(mCurrent.getLatLng(), child.getName());

        return mCurrent;
    }

    //Overridden
    @Override
    protected GvData newDatum() {
        mCurrent = (GvLocation) super.newDatum();
        return mCurrent;
    }

    @Override
    protected GvData launchDialogAndTestShowing() {
        mCurrent = (GvLocation) super.launchDialogAndTestShowing();
        return mCurrent;
    }
}
