package com.chdryra.android.reviewer;


import com.chdryra.android.myandroidwidgets.ClearableEditText;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class DialogCriterionFragment extends DialogBasicFragment {
	public static final int RESULT_DELETE_CRITERION = Activity.RESULT_FIRST_USER;
	public static final String OLD_NAME = "com.chdryra.android.reviewer.criterion_old_name";
	
	private Review mCriterion;
	private ClearableEditText mCriterionEditText;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, null);
		mCriterion = UtilReviewPackager.get(getArguments());
		
		mCriterionEditText = (ClearableEditText)v.findViewById(R.id.criterion_name_edit_text);
		mCriterionEditText.setText(mCriterion.getTitle().get());
		
		final AlertDialog dialog = buildDialog(v);
		
		mCriterionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		i.putExtra(OLD_NAME, mCriterion.getTitle().get());		
		if(resultCode == Activity.RESULT_OK)
			mCriterion.setTitle(mCriterionEditText.getText().toString());
		UtilReviewPackager.pack(mCriterion, i);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
}
