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

public class DialogProConFragment extends DialogBasicFragment {
	public static final String PROCON_OLD = "com.chdryra.android.reviewer.procon_old";
	
	private ClearableEditText mProCon;
	private String mOldProCon;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_procon, null);
		mProCon = (ClearableEditText)v.findViewById(R.id.procon_edit_text);
		
		mOldProCon = getArguments().getString(FragmentReviewProsCons.PROCON);
		String proConHint = getArguments().getString(FragmentReviewProsCons.PROCON_HINT);
		mProCon.setText(mOldProCon);		
		mProCon.setHint(proConHint);
		
		final AlertDialog dialog = buildDialog(v); 

		mProCon.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();

	            return false;
	        }
	    });

		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
		return dialog;
	}
		
	@Override
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		if(resultCode == Activity.RESULT_OK) {
			i.putExtra(PROCON_OLD, mOldProCon);
			i.putExtra(FragmentReviewProsCons.PROCON, mProCon.getText().toString());
		}
		
		if(resultCode == RESULT_DELETE)
			i.putExtra(PROCON_OLD, mOldProCon);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}	
}
