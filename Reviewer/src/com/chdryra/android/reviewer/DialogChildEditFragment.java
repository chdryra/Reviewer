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

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class DialogChildEditFragment extends SherlockDialogFragment{
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	public static final String CHILD_ID = "com.chdryra.android.reviewer.child_id";
	
	private ControllerReviewNode mChildController;
	
	private ClearableEditText mChildNameEditText;
	private RatingBar mChildRatingBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mChildController = Controller.unpack(getArguments());
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		final Dialog dialog = new Dialog(getSherlockActivity());
		dialog.setContentView(R.layout.dialog_child_add);

		mChildNameEditText = (ClearableEditText)dialog.findViewById(R.id.child_name_edit_text);
		mChildRatingBar = (RatingBar)dialog.findViewById(R.id.child_rating_bar);
		
		mChildNameEditText.setText(mChildController.getTitle());
		mChildRatingBar.setRating(mChildController.getRating());
		
		final Button deleteButton = (Button)dialog.findViewById(R.id.button_left);
		final Button cancelButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);
		
		mChildNameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		mChildNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	doneButton.performClick();
	            return false;
	        }
	    });

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
			}
		});
		
		deleteButton.setText(getResources().getString(R.string.button_delete_text));
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(RESULT_DELETE);
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_edit_criteria_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		if(resultCode == Activity.RESULT_OK) {
			String childName = mChildNameEditText.getText().toString();
			if(childName == null || childName.length() == 0)
				return;
			mChildController.setTitle(childName);
			mChildController.setRating(mChildRatingBar.getRating());
		}
		
		Intent i = new Intent();
		Controller.pack(mChildController, i);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		dismiss();
	}
}
