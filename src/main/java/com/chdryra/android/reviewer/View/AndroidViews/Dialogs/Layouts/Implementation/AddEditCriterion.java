/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Implementation;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditCriterion extends AddEditLayoutBasic<GvCriterion> {
    public static final int LAYOUT = R.layout.dialog_criterion_add_edit;
    public static final int SUBJECT = R.id.child_name_edit_text;
    public static final int RATING = R.id.child_rating_bar;
    public static final int[] VIEWS = new int[]{SUBJECT, RATING};

    //Constructors
    public AddEditCriterion(GvDataAdder adder) {
        super(GvCriterion.class, LAYOUT, VIEWS, SUBJECT, adder);
    }

    public AddEditCriterion(GvDataEditor editor) {
        super(GvCriterion.class, LAYOUT, VIEWS, SUBJECT, editor);
    }

    //Overridden
    @Override
    public GvCriterion createGvData() {
        String subject = ((EditText) getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) getView(RATING)).getRating();
        return new GvCriterion(subject, rating);
    }

    @Override
    public void updateLayout(GvCriterion data) {
        ((EditText) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }
}
