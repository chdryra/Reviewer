/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

/**
 * Dialog for editing sub-reviews: edit/delete subject and rating.
 */
public class DialogChildEditFragment extends DialogCancelDeleteDoneFragment {
    private ClearableEditText                             mChildNameEditText;
    private RatingBar                                     mChildRatingBar;
    private GVReviewSubjectRating                         mDatum;
    private InputHandlerReviewData<GVReviewSubjectRating> mHandler;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, null);

        mChildNameEditText = (ClearableEditText) v.findViewById(R.id.child_name_edit_text);
        mChildRatingBar = (RatingBar) v.findViewById(R.id.child_rating_bar);
        mChildNameEditText.setText(mDatum.getSubject());
        mChildRatingBar.setRating(mDatum.getRating());

        setKeyboardDoDoneOnEditText(mChildNameEditText);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new IHChildren();
        mDatum = mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                getArguments());
        setDialogTitle(getResources().getString(R.string.edit) + " " + mHandler.getGVType()
                .getDatumString());
        setDeleteWhatTitle(mDatum.getSubject());
    }

    @Override
    protected void onDeleteButtonClick() {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mDatum,
                createNewReturnData());
    }

    @Override
    protected boolean hasDataToDelete() {
        return true;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent data = createNewReturnData();
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mDatum, data);
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, createGVData(), data);
    }

    protected GVReviewSubjectRating createGVData() {
        return new GVReviewSubjectRating(mChildNameEditText.getText().toString().trim(),
                mChildRatingBar.getRating());
    }
}
