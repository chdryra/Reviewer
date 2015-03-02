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

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutChildReview extends GvDataViewLayout<GvChildList.GvChildReview> {
    public static final int   LAYOUT  = R.layout.dialog_criterion;
    public static final int   SUBJECT = R.id.child_name_edit_text;
    public static final int   RATING  = R.id.child_rating_bar;
    public static final int[] VIEWS   = new int[]{SUBJECT, RATING};

    public LayoutChildReview(GvDataViewAdd.GvDataAdder adder) {
        super(GvChildList.GvChildReview.class, LAYOUT, VIEWS, SUBJECT, adder);
    }

    public LayoutChildReview(GvDataViewEdit.GvDataEditor editor) {
        super(GvChildList.GvChildReview.class, LAYOUT, VIEWS, SUBJECT, editor);
    }

    @Override
    public String getTitleOnAdd(GvChildList.GvChildReview data) {
        float childRating = data.getRating();
        return data.getSubject() + ": " + RatingFormatter.outOfFive(childRating);
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvChildList.GvChildReview data) {
        return data.getSubject() + ": " + RatingFormatter.twoSignificantDigits(data.getRating());
    }

    @Override
    public GvChildList.GvChildReview createGvDataFromViews() {
        String subject = ((EditText) mViewHolder.getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) mViewHolder.getView(RATING)).getRating();
        return new GvChildList.GvChildReview(subject, rating);
    }

    @Override
    public void updateViews(GvChildList.GvChildReview data) {
        ((EditText) mViewHolder.getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) mViewHolder.getView(RATING)).setRating(data.getRating());
    }
//
//    @Override
//    public void clearViews() {
//        updateViews(new GvChildrenList().getNullItem());
//    }
}
