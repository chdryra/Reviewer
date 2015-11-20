/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditFactTest extends DialogGvDataEditTest<GvFactList.GvFact> {

    //Constructors
    public DialogEditFactTest() {
        super(ClassesAddEditViewDefault.EditFact.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        return new GvFactList.GvFact(mSolo.getEditText(0).getText().toString(),
                mSolo.getEditText(1).getText().toString());
    }
}

