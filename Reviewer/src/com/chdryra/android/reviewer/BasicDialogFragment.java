package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockDialogFragment;

public abstract class BasicDialogFragment extends SherlockDialogFragment {

	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;	
	
	@Override
	public abstract Dialog onCreateDialog(Bundle savedInstanceState); 

	@Override
	public void onStop() {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onStop();
	}
	
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		Intent i = new Intent();
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	protected AlertDialog buildDialog(View v) {
		return new AlertDialog.Builder(getActivity()).
				setView(v).
				setPositiveButton(R.string.dialog_button_change_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.dialog_button_cancel_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_CANCELED);
					}
				}).
				setNegativeButton(R.string.dialog_button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE);
					}
				}).
				create();
	}
}
