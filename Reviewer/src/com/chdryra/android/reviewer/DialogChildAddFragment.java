package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class DialogChildAddFragment extends DialogAddCancelDoneFragment{

	private ControllerReviewNode mController;
	private ControllerReviewNodeChildren mChildrenController;
	private ArrayList<String> mChildNames = new ArrayList<String>();
	
	private ClearableEditText mChildNameEditText;
	private RatingBar mChildRatingBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mChildrenController = mController.getChildrenController();
		for(String id : mChildrenController.getIDs())
			mChildNames.add(mChildrenController.getTitle(id));
	
		setDialogTitle(getResources().getString(R.string.dialog_add_criteria_title));
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_child_add, null);

		mChildNameEditText = (ClearableEditText)v.findViewById(R.id.child_name_edit_text);
		mChildRatingBar = (RatingBar)v.findViewById(R.id.child_rating_bar);
		
		setKeyboardIMEDoAction(mChildNameEditText);
		
		return v;
	}

	@Override
	protected void OnAddButtonClick() {
		String childName = mChildNameEditText.getText().toString();
		
		if(childName == null || childName.length() == 0)
			return;
		
		if(mChildNames.contains(childName)) {
			Toast.makeText(getSherlockActivity(), childName + ": " + getResources().getString(R.string.toast_exists_criterion), Toast.LENGTH_SHORT).show();
			return;
		}
		
		mChildrenController.addChild(childName, mChildRatingBar.getRating());
		mChildNames.add(childName);
		mChildNameEditText.setText(null);		
		mChildRatingBar.setRating(0);
		
		getDialog().setTitle("Added " + childName);
	}
	
	@Override
	protected void onDoneButtonClick() {
		OnAddButtonClick();
	}
}
