package com.chdryra.android.reviewer;

import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;

public class DialogChildAddFragment extends DialogAddCancelDoneFragment{
	public static final String SUBJECT = "com.chdryra.android.reviewer.subject";
	public static final String RATING = "com.chdryra.android.reviewer.rating";
	
	private LinkedList<String> mChildNames = new LinkedList<String>();	
	private ClearableEditText mChildNameEditText;
	private RatingBar mChildRatingBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ControllerReviewNodeChildren controller = Controller.unpack(getArguments()).getChildrenController();
		for(String id : controller.getIDs())
			mChildNames.add(controller.getTitle(id));
	
		setDialogTitle(getResources().getString(R.string.dialog_add_criteria_title));
		setAddOnDone(true);
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
		
		Intent i = getNewReturnData();
		i.putExtra(SUBJECT, childName);
		i.putExtra(RATING, mChildRatingBar.getRating());
		
		mChildNames.add(childName);
		mChildNameEditText.setText(null);		
		mChildRatingBar.setRating(0);
		
		getDialog().setTitle("Added " + childName);
	}
}
