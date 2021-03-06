/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.startouch.test.View.Dialogs;

import android.widget.RatingBar;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAddCriterionTest extends
        DialogGvDataAddTest<GvCriterion> {

    //Constructors
    public DialogAddCriterionTest() {
        super(UiAndroid.DefaultLaunchables.AddCriterion.class);
    }

    //protected methods
    @Override
    protected boolean isDataNulled() {
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);

        return super.isDataNulled() && rb.getRating() == 0;
    }
}
