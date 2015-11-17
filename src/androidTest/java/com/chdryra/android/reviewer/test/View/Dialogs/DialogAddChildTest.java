/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.widget.RatingBar;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddChildTest extends
        DialogGvDataAddTest<GvCriterionList.GvCriterion> {

    //Constructors
    public DialogAddChildTest() {
        super(ClassesAddEditViewDefault.AddChild.class);
    }

    //protected methods
    @Override
    protected boolean isDataNulled() {
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);

        return super.isDataNulled() && rb.getRating() == 0;
    }
}
