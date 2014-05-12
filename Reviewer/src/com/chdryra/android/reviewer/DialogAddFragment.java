package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockDialogFragment;

public abstract class DialogAddFragment extends SherlockDialogFragment {

	public static final int RESULT_ADD = Activity.RESULT_FIRST_USER;
	protected Button mActionButton;
	protected Button mCancelButton;
	protected Button mDoneButton;
	
	protected abstract View getDialogUI(); 
	protected abstract void doActionButtonClick();
	protected abstract String getDialogTitle();
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return buildDialog();
	}
	
	@Override
	public void onStop() {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onStop();
	}
	
	protected void setIMEDoAction(EditText editText) {
		editText.setImeOptions(EditorInfo.IME_ACTION_GO);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
	            	mActionButton.performClick();
	            return false;
	        }
	    });
	}
	
	protected void setIMEDoDone(EditText editText) {
		editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	mDoneButton.performClick();
	            return false;
	        }
	    });
	}
	
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());		
		dismiss();
	}

	protected View getButtons() {	
		View buttons = getActivity().getLayoutInflater().inflate(R.layout.dialog_button_layout, null);
		
		mActionButton = (Button)buttons.findViewById(R.id.button_left);
		mCancelButton = (Button)buttons.findViewById(R.id.button_middle);
		mDoneButton = (Button)buttons.findViewById(R.id.button_right);

		mActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doActionButtonClick();
			}
		});

		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doCancelButtonClick();
			}
		});
		
		mDoneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doDoneButtonClick();
			}
		});
		
		return buttons;
	}

	protected void doDoneButtonClick() {
		sendResult(Activity.RESULT_OK);
	}

	protected void doCancelButtonClick() {
		sendResult(Activity.RESULT_CANCELED);
	}
	
	protected Dialog buildDialog() {
			
		Dialog dialog = new Dialog(getActivity());
		
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		layout.addView(getDialogUI());
		layout.addView(getButtons());
		
		dialog.setContentView(layout);
		dialog.setTitle(getDialogTitle());
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}
}
