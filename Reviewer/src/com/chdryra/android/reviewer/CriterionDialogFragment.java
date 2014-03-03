package com.chdryra.android.reviewer;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class CriterionDialogFragment extends BasicDialogFragment {

	public static final String EXTRA_CRITERION_NEW_NAME = "com.chdryra.android.reviewer.criterion_new_name";
	public static final String EXTRA_CRITERION_OLD_NAME = "com.chdryra.android.reviewer.criterion_old_name";

	public static final int RESULT_DELETE_CRITERION = Activity.RESULT_FIRST_USER;
	
	private String mCriterionOldName;
	private EditText mCriterionName;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, null);
		mCriterionName = (EditText)v.findViewById(R.id.criterion_name_edit_text);
		if( getTargetRequestCode() == ReviewCreateFragment.CRITERION_EDIT )
		{
			mCriterionOldName = (String)getArguments().getSerializable(ReviewCreateFragment.CRITERION_NAME);
			mCriterionName.setText(mCriterionOldName);
		}
		
		final AlertDialog dialog = buildDialog(v);
		
		mCriterionName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();

	            return true;
	        }
	    });

		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		return dialog;
		}

	@Override
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED)
			return;
		
		Intent i = new Intent();
		
		if (resultCode== RESULT_DELETE_CRITERION)
			i.putExtra(EXTRA_CRITERION_OLD_NAME, mCriterionOldName);	
		
		if (resultCode == Activity.RESULT_OK)
		{
			i.putExtra(EXTRA_CRITERION_OLD_NAME, mCriterionOldName);	
			i.putExtra(EXTRA_CRITERION_NEW_NAME, mCriterionName.getText().toString());
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	}
