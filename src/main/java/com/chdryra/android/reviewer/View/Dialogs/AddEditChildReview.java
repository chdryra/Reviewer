/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditChildReview extends AddEditLayout<GvChildReviewList.GvChildReview> {
    public static final int LAYOUT = R.layout.dialog_criterion_add_edit;
    public static final int   SUBJECT = R.id.child_name_edit_text;
    public static final int   RATING  = R.id.child_rating_bar;
    public static final int[] VIEWS   = new int[]{SUBJECT, RATING};

    public AddEditChildReview(GvDataAdder adder) {
        super(GvChildReviewList.GvChildReview.class, LAYOUT, VIEWS, SUBJECT, adder);
    }

    public AddEditChildReview(GvDataEditor editor) {
        super(GvChildReviewList.GvChildReview.class, LAYOUT, VIEWS, SUBJECT, editor);
    }

    @Override
    public GvChildReviewList.GvChildReview createGvData() {
        String subject = ((EditText) getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) getView(RATING)).getRating();
        return new GvChildReviewList.GvChildReview(subject, rating);
    }

    @Override
    public void updateLayout(GvChildReviewList.GvChildReview data) {
        ((EditText) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }
}
