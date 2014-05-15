package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogChildEditFragment extends DialogDeleteCancelDoneFragment{
	public static final String CHILD_ID = "com.chdryra.android.reviewer.child_id";
	
	private ControllerReviewNode mChildController;	
	private ClearableEditText mChildNameEditText;
	private RatingBar mChildRatingBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mChildController = Controller.unpack(getArguments());
		setDialogTitle(getResources().getString(R.string.dialog_edit_criteria_title));
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_criterion_title));
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_child_add, null);

		mChildNameEditText = (ClearableEditText)v.findViewById(R.id.child_name_edit_text);
		mChildRatingBar = (RatingBar)v.findViewById(R.id.child_rating_bar);
		mChildNameEditText.setText(mChildController.getTitle());
		mChildRatingBar.setRating(mChildController.getRating());

		setKeyboardIMEDoDone(mChildNameEditText);
		
		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		String childName = mChildNameEditText.getText().toString();
		if(childName == null || childName.length() == 0)
			return;
		
		mChildController.setTitle(childName);
		mChildController.setRating(mChildRatingBar.getRating());
		Controller.pack(mChildController, getReturnData());
	}
	
	@Override
	protected void onDeleteButtonClick() {
		Controller.pack(mChildController, getReturnData());
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mChildController != null;
	}
}
