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
	public static final int RESULT_BROWSE = 4;
	
	private static final String TAG = "DialogURLFragment";
	
	private ControllerReviewNode mController;
	private ClearableEditText mURLEditText;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		mController = Controller.unpack(getArguments());
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_url, null);
		
		mURLEditText = (ClearableEditText)v.findViewById(R.id.url_edit_text);
		if(mController.hasURL())
			mURLEditText.setText(mController.getURLString());
	
		final AlertDialog dialog = buildDialog(v);
		
		mURLEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	sendResult(Activity.RESULT_OK);	            	     
	            
	            return true;
	        }
	    });

		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		return dialog;

	}
	
	@Override
	protected void sendResult(int resultCode) {
		if (resultCode != Activity.RESULT_OK || resultCode != RESULT_BROWSE) {
			super.sendResult(resultCode);
			return;
		}

		String urlText = mURLEditText.getText().toString();
		if( urlText.length() > 0) {
			try {
				mController.setURL(urlText);
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
		mController.deleteURL();
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.dialog_URL_title);
	}
	
	@Override
	protected AlertDialog buildDialog(View v, String title) {
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
				setView(v).
				setPositiveButton(R.string.dialog_button_done_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.dialog_button_browse_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_BROWSE);
					}
				}).
				setNegativeButton(R.string.dialog_button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE);
					}
				}).
				create(); 
		if(title != null)
			dialog.setTitle(title);
		
		return dialog;
	}
}
