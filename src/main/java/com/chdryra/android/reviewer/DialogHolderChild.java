/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;
import android.widget.RatingBar;

import java.text.DecimalFormat;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * {@link DialogHolderAddEdit}: child reviews
 */
class DialogHolderChild extends DialogHolderAddEdit<GvChildrenList.GvChildReview> {
    private static final int                          LAYOUT    = R.layout.dialog_criterion;
    private static final int                          SUBJECT   = R.id.child_name_edit_text;
    private static final int                          RATING    = R.id.child_rating_bar;
    private static final GvChildrenList.GvChildReview NULL_DATA = new GvChildrenList
            .GvChildReview(null, 0);

    DialogHolderChild(DialogGvDataAddFragment<GvChildrenList.GvChildReview> dialogAdd) {
        super(LAYOUT, new int[]{SUBJECT, RATING}, SUBJECT, dialogAdd, NULL_DATA);
    }

    DialogHolderChild(DialogGvDataEditFragment<GvChildrenList.GvChildReview> dialogEdit) {
        super(LAYOUT, new int[]{SUBJECT, RATING}, SUBJECT, dialogEdit);
    }

    @Override
    protected String getDialogTitleOnAdd(GvChildrenList.GvChildReview data) {
        float childRating = data.getRating();
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);
        return data.getSubject() + ": " + rating + "/" + "5";
    }

    @Override
    protected String getDeleteConfirmDialogTitle(GvChildrenList.GvChildReview data) {
        return data.getSubject() + ": " + data.getRating();

    }

    @Override
    protected GvChildrenList.GvChildReview createGvDataFromViews() {
        String subject = ((EditText) getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) getView(RATING)).getRating();
        return new GvChildrenList.GvChildReview(subject, rating);
    }

    @Override
    protected void updateViews(GvChildrenList.GvChildReview data) {
        ((EditText) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }
}
