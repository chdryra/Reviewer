/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.text.DecimalFormat;

/**
 * Dialog for adding sub-reviews: asks for a subject and rating.
 */
public class DialogChildAddFragment extends DialogAddReviewDataFragment {
    public static final String SUBJECT = "com.chdryra.android.reviewer.subject";
    public static final String RATING  = "com.chdryra.android.reviewer.rating";

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
    protected void onAddButtonClick() {
        String childName = mChildNameEditText.getText().toString();
        float childRating = mChildRatingBar.getRating();

        if (childName == null || childName.length() == 0) {
            return;
        }

        GVReviewSubjectRatingList children = (GVReviewSubjectRatingList) getData();
        if (children.contains(childName)) {
            Toast.makeText(getActivity(), childName + ": " + getResources().getString(R.string
                    .toast_exists_criterion), Toast.LENGTH_SHORT).show();
            return;
        }

        children.add(childName, childRating);

        Intent i = getNewReturnDataIntent();
        i.putExtra(SUBJECT, childName);
        i.putExtra(RATING, childRating);

        mChildNameEditText.setText(null);
        mChildRatingBar.setRating(0);

        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);

        getDialog().setTitle("+ " + childName + ": " + rating + "/" + "5");
    }
}
