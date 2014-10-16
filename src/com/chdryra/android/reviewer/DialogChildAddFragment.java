/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

import java.text.DecimalFormat;

/**
 * Dialog for adding sub-reviews: asks for a subject and rating.
 */
public class DialogChildAddFragment extends DialogAddReviewDataFragment<GVReviewSubjectRating> {
    private ClearableEditText mChildNameEditText;
    private RatingBar         mChildRatingBar;

    public DialogChildAddFragment() {
        super(GVType.CHILDREN);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, parent,
                false);
        mChildNameEditText = (ClearableEditText) v.findViewById(R.id.child_name_edit_text);
        mChildRatingBar = (RatingBar) v.findViewById(R.id.child_rating_bar);

        setKeyboardDoActionOnEditText(mChildNameEditText);

        return v;
    }

    @Override
    protected GVReviewSubjectRating createGVData() {
        return new GVReviewSubjectRating(mChildNameEditText.getText().toString().trim(),
                mChildRatingBar.getRating());
    }

    @Override
    protected void resetDialogOnAdd(GVReviewSubjectRating newDatum) {
        //Reset dialog inputs
        mChildNameEditText.setText(null);
        mChildRatingBar.setRating(0);

        //Set dialog title
        float childRating = newDatum.getRating();
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);
        getDialog().setTitle("+ " + newDatum.getSubject() + ": " + rating + "/" + "5");
    }
}
