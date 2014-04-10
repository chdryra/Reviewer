package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DialogDeleteConfirmFragment extends SherlockDialogFragment {

	private static final String DELETE = "Delete";
	public static final String DELETE_WHAT = "com.chdryra.android.reviewer.delete_what";
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {	
		String deleteWhat = getArguments().getString(DELETE_WHAT);
		String title = deleteWhat != null? DELETE + " " + deleteWhat + "?" : DELETE + "?";
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
				setTitle(title).
				setPositiveButton(R.string.dialog_button_yes_text, new DialogInterface.OnClickListener() {
					
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
				create(); 		

		return dialog;
	}
	
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED)
			return;
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
	}
}
