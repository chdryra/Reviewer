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

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

import java.text.DecimalFormat;

/**
 * Dialog for adding sub-reviews: asks for a subject and rating.
 */
public class DialogChildAddFragment extends DialogAddReviewDataFragment {
    private ClearableEditText                             mChildNameEditText;
    private RatingBar                                     mChildRatingBar;
    private InputHandlerReviewData<GVReviewSubjectRating> mInputHandler;

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
        mInputHandler = new IHChildren((GVReviewSubjectRatingList) getData());

        return v;
    }

    @Override
    protected void onAddButtonClick() {
        GVReviewSubjectRating tag = createGVData();
        if (mInputHandler.isNewAndValid(tag, getActivity())) {
            Intent data = createNewReturnData();
            mInputHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, tag, data);
            mInputHandler.add(data, getActivity());
            resetDialog();
            setDialogAddedTitle(tag);
        }
    }

    GVReviewSubjectRating createGVData() {
        String childName = mChildNameEditText.getText().toString().trim();
        float childRating = mChildRatingBar.getRating();
        return new GVReviewSubjectRating(childName, childRating);
    }

    void resetDialog() {
        mChildNameEditText.setText(null);
        mChildRatingBar.setRating(0);
    }

    void setDialogAddedTitle(GVReviewSubjectRating child) {
        float childRating = child.getRating();
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String rating = childRating % 1L > 0L ? decimalFormatter.format(childRating) : formatter
                .format(childRating);

        getDialog().setTitle("+ " + child.getSubject() + ": " + rating + "/" + "5");
    }
}
