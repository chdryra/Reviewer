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

import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

import java.text.DecimalFormat;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DHChild extends DialogHolderBasic<GVReviewSubjectRating> {
    private static final int                   LAYOUT    = R.layout.dialog_criterion;
    private static final int                   SUBJECT   = R.id.child_name_edit_text;
    private static final int                   RATING    = R.id.child_rating_bar;
    private static final GVReviewSubjectRating NULL_DATA = new GVReviewSubjectRating(null, 0);

    DHChild(DialogAddReviewDataFragment<GVReviewSubjectRating> dialogAdd) {
        super(LAYOUT, new int[]{SUBJECT, RATING});
        UIDialog.UIDialogManager<GVReviewSubjectRating,
                DialogAddReviewDataFragment<GVReviewSubjectRating>> manager = new
                UIDialog.UIDialogManager<GVReviewSubjectRating,
                        DialogAddReviewDataFragment<GVReviewSubjectRating>>() {
                    @Override
                    public void initialise(GVReviewSubjectRating data,
                                           DialogAddReviewDataFragment<GVReviewSubjectRating>
                            dialog) {
                        dialog.setKeyboardDoActionOnEditText(getEditTextKeyboardDoAction());
                    }

                    @Override
                    public void update(GVReviewSubjectRating data,
                                       DialogAddReviewDataFragment<GVReviewSubjectRating> dialog) {
                        updateInputs(NULL_DATA);
                        dialog.getDialog().setTitle(getDialogTitleOnAdd(data));
                    }

                    @Override
                    public GVReviewSubjectRating getGVData() {
                        return createGVData();
                    }
                };

        setDialogUI(new UIDialog<GVReviewSubjectRating,
                DialogAddReviewDataFragment<GVReviewSubjectRating>>(dialogAdd, manager));
    }

    DHChild(DialogEditReviewDataFragment<GVReviewSubjectRating> dialogEdit) {
        super(LAYOUT, new int[]{SUBJECT, RATING});
        UIDialog.UIDialogManager<GVReviewSubjectRating,
                DialogEditReviewDataFragment<GVReviewSubjectRating>> manager = new
                UIDialog.UIDialogManager<GVReviewSubjectRating,
                        DialogEditReviewDataFragment<GVReviewSubjectRating>>() {

                    @Override
                    public void initialise(GVReviewSubjectRating data,
                                           DialogEditReviewDataFragment<GVReviewSubjectRating>
                                                   dialog) {
                        updateInputs(data);
                        dialog.setKeyboardDoDoneOnEditText(getEditTextKeyboardDoDone());
                        dialog.setDeleteWhatTitle(getDialogDeleteConfirmTitle(data));
                    }

                    @Override
                    public void update(GVReviewSubjectRating data,
                                       DialogEditReviewDataFragment<GVReviewSubjectRating> dialog) {

                    }

                    @Override
                    public GVReviewSubjectRating getGVData() {
                        return createGVData();
                    }
                };

        setDialogUI(new UIDialog<GVReviewSubjectRating,
                DialogEditReviewDataFragment<GVReviewSubjectRating>>(dialogEdit,
                manager));
    }

    protected EditText getEditTextKeyboardDoAction() {
        return (EditText) getView(SUBJECT);
    }

    protected EditText getEditTextKeyboardDoDone() {
        return (EditText) getView(SUBJECT);
    }

    protected String getDialogTitleOnAdd(GVReviewSubjectRating data) {
        float childRating = data.getRating();
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);
        return "+ " + data.getSubject() + ": " + rating + "/" + "5";
    }

    protected String getDialogDeleteConfirmTitle(GVReviewSubjectRating data) {
        return data.getSubject() + ": " + data.getRating();

    }

    protected GVReviewSubjectRating createGVData() {
        String subject = ((EditText) getView(SUBJECT)).getText().toString().trim();
        float rating = ((RatingBar) getView(RATING)).getRating();
        return new GVReviewSubjectRating(subject, rating);
    }

    protected void updateInputs(GVReviewSubjectRating data) {
        ((EditText) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }
}
