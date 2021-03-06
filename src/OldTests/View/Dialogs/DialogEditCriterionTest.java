/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.startouch.test.View.Dialogs;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogEditCriterionTest extends
        DialogGvDataEditTest<GvCriterion> {

    //Constructors
    public DialogEditCriterionTest() {
        super(UiAndroid.DefaultLaunchables.EditCriterion.class);
    }

    //protected methods
    @Override
    protected GvData getDataShown() {
        EditText et = mSolo.getEditText(0);
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);
        return new GvCriterion(et.getText().toString(), rb.getRating());
    }
}

