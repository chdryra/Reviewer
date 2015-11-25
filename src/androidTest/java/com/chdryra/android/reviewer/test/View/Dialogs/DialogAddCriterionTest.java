/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test.View.Dialogs;

import android.widget.RatingBar;

import com.chdryra.android.reviewer.View.Implementation.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddCriterionTest extends
        DialogGvDataAddTest<GvCriterion> {

    //Constructors
    public DialogAddCriterionTest() {
        super(DefaultLaunchables.AddCriterion.class);
    }

    //protected methods
    @Override
    protected boolean isDataNulled() {
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);

        return super.isDataNulled() && rb.getRating() == 0;
    }
}
