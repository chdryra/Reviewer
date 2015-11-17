/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 March, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditLocationTest extends DialogGvDataEditTest<GvLocationList.GvLocation> {
    private GvLocationList.GvLocation mCurrent;

    //Constructors
    public DialogEditLocationTest() {
        super(ClassesAddEditViewDefault.EditLocation.class);
    }

    //protected methods
    @Override
    protected GvLocationList.GvLocation getDataShown() {
        String name = mSolo.getEditText(0).getText().toString().trim();
        mCurrent = new GvLocationList.GvLocation(mCurrent.getLatLng(), name);

        return mCurrent;
    }

    @Override
    protected GvData getEditDatum() {
        GvLocationList.GvLocation child = (GvLocationList.GvLocation) super.getEditDatum();

        mCurrent = new GvLocationList.GvLocation(mCurrent.getLatLng(), child.getName());

        return mCurrent;
    }

//Overridden
    @Override
    protected GvData newDatum() {
        mCurrent = (GvLocationList.GvLocation) super.newDatum();
        return mCurrent;
    }

    @Override
    protected GvData launchDialogAndTestShowing() {
        mCurrent = (GvLocationList.GvLocation) super.launchDialogAndTestShowing();
        return mCurrent;
    }
}
