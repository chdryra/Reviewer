/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Implementation.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditFactTest extends DialogGvDataEditTest<GvFact> {

    //Constructors
    public DialogEditFactTest() {
        super(DefaultLaunchables.EditFact.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        return new GvFact(mSolo.getEditText(0).getText().toString(),
                mSolo.getEditText(1).getText().toString());
    }
}

