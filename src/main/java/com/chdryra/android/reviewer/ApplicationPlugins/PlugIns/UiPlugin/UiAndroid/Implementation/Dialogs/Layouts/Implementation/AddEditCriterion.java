/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditCriterion extends AddEditLayoutBasic<GvCriterion> {
    private static final int LAYOUT = R.layout.dialog_criterion_add_edit;
    private static final int SUBJECT = R.id.child_name_edit_text;
    private static final int RATING = R.id.child_rating_bar;

    //Constructors
    public AddEditCriterion(GvDataAdder adder) {
        super(GvCriterion.class, new LayoutHolder(LAYOUT, SUBJECT, RATING), SUBJECT, adder);
    }

    public AddEditCriterion(GvDataEditor editor) {
        super(GvCriterion.class, new LayoutHolder(LAYOUT, SUBJECT, RATING), SUBJECT, editor);
    }

    //Overridden
    @Override
    public GvCriterion createGvDataFromInputs() {
        String subject = ((EditText) getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) getView(RATING)).getRating();
        return new GvCriterion(subject, rating);
    }

    @Override
    public void updateView(GvCriterion data) {
        ((EditText) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }
}
