/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;
import android.widget.RatingBar;

import java.text.DecimalFormat;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogGvChildReview extends DialogGvDataBasic<GvChildrenList.GvChildReview> {
    private static final int                          LAYOUT    = R.layout.dialog_criterion;
    private static final int                          SUBJECT   = R.id.child_name_edit_text;
    private static final int                          RATING    = R.id.child_rating_bar;
    private static final int[]                        VIEWS     = new int[]{SUBJECT, RATING};
    private static final GvChildrenList.GvChildReview NULL_DATA = new GvChildrenList
            .GvChildReview(null, 0);

    DialogGvChildReview(DialogGvDataAddFragment<GvChildrenList.GvChildReview> dialogAdd) {
        super(LAYOUT, VIEWS, SUBJECT, dialogAdd, NULL_DATA);
    }

    DialogGvChildReview(DialogGvDataEditFragment<GvChildrenList.GvChildReview> dialogEdit) {
        super(LAYOUT, VIEWS, SUBJECT, dialogEdit);
    }

    @Override
    public String getDialogTitleOnAdd(GvChildrenList.GvChildReview data) {
        float childRating = data.getRating();
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);
        return data.getSubject() + ": " + rating + "/" + "5";
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvChildrenList.GvChildReview data) {
        return data.getSubject() + ": " + data.getRating();

    }

    @Override
    public GvChildrenList.GvChildReview createGvDataFromViews() {
        String subject = ((EditText) mViewHolder.getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) mViewHolder.getView(RATING)).getRating();
        return new GvChildrenList.GvChildReview(subject, rating);
    }

    @Override
    public void updateViews(GvChildrenList.GvChildReview data) {
        ((EditText) mViewHolder.getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) mViewHolder.getView(RATING)).setRating(data.getRating());
    }
}
