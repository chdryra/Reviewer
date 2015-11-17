/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddFactTest extends
        DialogGvDataAddTest<GvFactList.GvFact> {

    //Constructors
    public DialogAddFactTest() {
        super(ClassesAddEditViewDefault.AddFact.class);
    }

    //protected methods
    @Override
    protected boolean isDataEntered() {
        return mSolo.getEditText(0).getText().toString().length() > 0 && mSolo.getEditText(1)
                .getText().toString().length() > 0;
    }

    @Override
    protected boolean isDataNulled() {
        return mSolo.getEditText(0).getText().toString().length() == 0 && mSolo.getEditText(1)
                .getText().toString().length() == 0;
    }
}
