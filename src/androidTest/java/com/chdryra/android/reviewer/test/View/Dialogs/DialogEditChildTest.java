/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditChildTest extends
        DialogGvDataEditTest<GvCriterionList.GvCriterion> {

    //Constructors
    public DialogEditChildTest() {
        super(ClassesAddEditViewDefault.EditChild.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        EditText et = mSolo.getEditText(0);
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);
        return new GvCriterionList.GvCriterion(et.getText().toString(), rb.getRating());
    }
}

