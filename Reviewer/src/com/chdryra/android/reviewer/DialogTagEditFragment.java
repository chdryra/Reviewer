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
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;

public class DialogTagEditFragment extends SherlockDialogFragment{
	private ControllerReviewNode mController;
	
	private String mCurrentTag;
	private ArrayList<String> mTags;	
	private ClearableAutoCompleteTextView mTagEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mTags = mController.hasTags()? mController.getTags() : new ArrayList<String>();
		mCurrentTag = getArguments().getString(FragmentReviewCreate.TAG_EDIT_STRING);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		final Dialog dialog = new Dialog(getSherlockActivity());
		dialog.setContentView(R.layout.dialog_tag);

		mTagEditText = (ClearableAutoCompleteTextView)dialog.findViewById(R.id.tag_edit_text);
		mTagEditText.setText(mCurrentTag);

		final Button cancelButton = (Button)dialog.findViewById(R.id.button_left);
		final Button deleteButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);
		
		mTagEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		mTagEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
				mTagEditText.setText(null);
				sendResult(Activity.RESULT_OK);
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_edit_tag_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void editTag() {
		String tag = mTagEditText.getText().toString();
		
		mTags.remove(mCurrentTag);
		if(tag.length() > 0)
			mTags.add(tag);
			
		getDialog().setTitle("Changed: " + mCurrentTag + " to: " + tag);
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		if(resultCode == Activity.RESULT_OK) {
			editTag();
			mController.removeTags();
			mController.addTags(mTags);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
		dismiss();
	}

}
