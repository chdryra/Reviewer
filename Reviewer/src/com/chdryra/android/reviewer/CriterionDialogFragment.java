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

public class CriterionDialogFragment extends SherlockDialogFragment {

	public static final String EXTRA_CRITERION_NEW_NAME = "com.chdryra.android.reviewer.criterion_new_name";
	public static final String EXTRA_CRITERION_OLD_NAME = "com.chdryra.android.reviewer.criterion_old_name";

	public static final int RESULT_DELETE_CRITERION = Activity.RESULT_FIRST_USER;
	
	private String mCriterionOldName;
	private String mCriterionNewName;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, null);
		EditText editText = (EditText)v.findViewById(R.id.criterion_name_edit_text);
		if( getTargetRequestCode() == ReviewerDefineFragment.CRITERION_EDIT )
		{
			mCriterionOldName = (String)getArguments().getSerializable(ReviewerDefineFragment.CRITERION_NAME);
			editText.setText(mCriterionOldName);
		}
		
		final AlertDialog dialog = buildDialog(v);
		
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String newName = s.toString().trim();
				if(newName.length() > 0)
					mCriterionNewName = newName;
				else
					mCriterionNewName = null;
			}
		});
		
		editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        }
		    }
		});
		
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            {
	            	dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
	            }
	            return true;
	        }
	    });
	 
		return dialog;
		}

	private void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
	}
	
	@Override
	public void onStop() {
		hideKeyboard();
		super.onStop();
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		
		if (resultCode== RESULT_DELETE_CRITERION) {
			i.putExtra(EXTRA_CRITERION_OLD_NAME, mCriterionOldName);	
		}
		
		if (resultCode == Activity.RESULT_OK)
		{
			i.putExtra(EXTRA_CRITERION_OLD_NAME, mCriterionOldName);	
			i.putExtra(EXTRA_CRITERION_NEW_NAME, mCriterionNewName);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	private AlertDialog buildDialog(View v) {
		return new AlertDialog.Builder(getActivity()).
				setView(v).
				setTitle(R.string.criterion_edit_dialog_title).
				setPositiveButton(R.string.button_done_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.button_cancel_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_CANCELED);
					}
				}).
				setNegativeButton(R.string.button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE_CRITERION);
					}
				}).
				create();
	}
}
