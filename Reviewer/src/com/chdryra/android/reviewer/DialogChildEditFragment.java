package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogChildEditFragment extends DialogDeleteCancelDoneFragment{
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
		setDialogTitle(getResources().getString(R.string.dialog_edit_criteria_title));
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_criterion_title));
	}

	@Override
	protected View createDialogUI() {
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
