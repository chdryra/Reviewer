package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class DialogURLFragment extends DialogBasicFragment {
	private static final String TAG = "DialogURLFragment";
	
	private Controller mController = Controller.getInstance();
	private RDId mReviewID;
	ClearableEditText mURLEditText;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_url, null);
		
		mURLEditText = (ClearableEditText)v.findViewById(R.id.url_edit_text);
		mReviewID = (RDId)getArguments().getParcelable(FragmentReviewOptions.REVIEW_ID);
		if(mController.hasURL(mReviewID))
			mURLEditText.setText(mController.getURLString(mReviewID));
	
		final AlertDialog dialog = buildDialog(v);
		
		mURLEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		if (resultCode != Activity.RESULT_OK) {
			super.sendResult(resultCode);
			return;
		}

		String urlText = mURLEditText.getText().toString();
		if( urlText.length() > 0) {
			try {
				mController.setURL(mReviewID, urlText);
			} catch (Exception e) {
				Log.e(TAG, "Malformed URL", e);
				Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_bad_url), Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
	
	}
	
	@Override
	protected void deleteData() {
		mController.deleteURL(mReviewID);
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.dialog_URL_title);
	}
}
