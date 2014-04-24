package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class DialogChildAdd extends SherlockDialogFragment{

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
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		final Dialog dialog = new Dialog(getSherlockActivity());
		dialog.setContentView(R.layout.dialog_child_add);

		mChildNameEditText = (ClearableEditText)dialog.findViewById(R.id.child_name_edit_text);
		mChildRatingBar = (RatingBar)dialog.findViewById(R.id.child_rating_bar);
		final Button cancelButton = (Button)dialog.findViewById(R.id.button_left);
		final Button addButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);
		
		mChildNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
	            	addButton.performClick();
	            return false;
	        }
	    });

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
				dialog.dismiss();
			}
		});
		
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addChild();
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
				dialog.dismiss();
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_add_criteria_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void addChild() {
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

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED)
			return;
		
		if(resultCode == Activity.RESULT_OK)
			addChild();
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());	
	}
	
}
