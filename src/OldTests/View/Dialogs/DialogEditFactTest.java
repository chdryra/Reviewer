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
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditFactTest extends DialogGvDataEditTest<GvFact> {

    //Constructors
    public DialogEditFactTest() {
        super(UiAndroid.DefaultLaunchables.EditFact.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        return new GvFact(mSolo.getEditText(0).getText().toString(),
                mSolo.getEditText(1).getText().toString());
    }
}

