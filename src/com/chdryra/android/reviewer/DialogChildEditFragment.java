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

@SuppressWarnings("WeakerAccess")
public class DialogChildEditFragment extends DialogCancelDeleteDoneFragment {
	public static final String SUBJECT = "com.chdryra.android.reviewer.subject_edit";
	public static final String SUBJECT_OLD = "com.chdryra.android.reviewer.subject_edit_old";
	public static final String RATING = "com.chdryra.android.reviewer.rating_edit";
	
	private ClearableEditText mChildNameEditText;
	private RatingBar mChildRatingBar;
	private String mSubject;
	private float mRating;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSubject = getArguments().getString(FragmentReviewChildren.CHILD_SUBJECT);
		mRating = getArguments().getFloat(FragmentReviewChildren.CHILD_RATING);
		setDialogTitle(getResources().getString(R.string.dialog_edit_criterion_title));
		setDeleteWhatTitle(mSubject);
	}

	@Override
	protected View createDialogUI(ViewGroup parent) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, null);
		mChildNameEditText = (ClearableEditText)v.findViewById(R.id.child_name_edit_text);
		mChildRatingBar = (RatingBar)v.findViewById(R.id.child_rating_bar);
		mChildNameEditText.setText(mSubject);
		mChildRatingBar.setRating(mRating);
		setKeyboardIMEDoDone(mChildNameEditText);
		
		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		String childName = mChildNameEditText.getText().toString();
		if(childName == null || childName.length() == 0)
			return;
		
		Intent i = getNewReturnData();
		i.putExtra(SUBJECT_OLD, mSubject);
		i.putExtra(SUBJECT, childName);
		i.putExtra(RATING, mChildRatingBar.getRating());
	}
	
	@Override
	protected void onDeleteButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(SUBJECT_OLD, mSubject);
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return true;
	}
}
