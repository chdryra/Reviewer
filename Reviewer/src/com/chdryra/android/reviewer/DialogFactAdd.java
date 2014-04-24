package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

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
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class DialogFactAdd extends SherlockDialogFragment{

	private ControllerReviewNode mController;
	private LinkedHashMap<String, String> mFacts;
	
	private ClearableEditText mFactLabelEditText;
	private ClearableEditText mFactValueEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mFacts = mController.hasFacts()? mController.getFacts() : new LinkedHashMap<String, String>();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		final Dialog dialog = new Dialog(getSherlockActivity());
		dialog.setContentView(R.layout.dialog_fact_add);

		mFactLabelEditText = (ClearableEditText)dialog.findViewById(R.id.fact_label_edit_text);
		mFactValueEditText = (ClearableEditText)dialog.findViewById(R.id.fact_value_edit_text);

		final Button cancelButton = (Button)dialog.findViewById(R.id.button_left);
		final Button addButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);
		
		mFactValueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
				addFact();
				
				mFactLabelEditText.requestFocus();
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
				dialog.dismiss();
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_add_fact_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void addFact() {
		String label = mFactLabelEditText.getText().toString();
		String value = mFactValueEditText.getText().toString();
		if((label == null || label.length() == 0) && (value == null || value.length() == 0))
			return;
		
		if(label == null || label.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_label), Toast.LENGTH_SHORT).show();
		else if(value == null || value.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_value), Toast.LENGTH_SHORT).show();
		else {
			mFacts.put(label, value);
			mFactLabelEditText.setText(null);
			mFactValueEditText.setText(null);
			getDialog().setTitle("Added " + label + ": " + value);
		}
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED)
			return;
		
		if(resultCode == Activity.RESULT_OK) {
			addFact();
			mController.setFacts(mFacts);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());	
	}
	
}
