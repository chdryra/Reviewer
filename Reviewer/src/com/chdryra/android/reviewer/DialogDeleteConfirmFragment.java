package com.chdryra.android.reviewer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DialogDeleteConfirmFragment extends SherlockDialogFragment {

	private static final String DELETE = "Delete";
	public static final String DELETE_WHAT = "com.chdryra.android.reviewer.delete_what";
	private static final String DELETE_CONFIRM_TAG = "DeleteConfirm";
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {	
		String deleteWhat = getArguments().getString(DELETE_WHAT);
		String title = deleteWhat != null? DELETE + " " + deleteWhat + "?" : DELETE + "?";
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
				setTitle(title).
				setPositiveButton(R.string.button_yes_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(ActivityResultCode.OK);
					}
				}).
				setNeutralButton(R.string.button_cancel_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(ActivityResultCode.CANCEL);
					}
				}).
				create(); 		

		return dialog;
	}
	
	protected void sendResult(ActivityResultCode resultCode) {
		if (getTargetFragment() == null || resultCode.equals(ActivityResultCode.CANCEL))
			return;
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode.get(), new Intent());
	}
	
	public static void showDeleteConfirmDialog(String deleteWhat, Fragment targetFragment, int requestCode, FragmentManager fragmentManager) {
		DialogDeleteConfirmFragment dialog = new DialogDeleteConfirmFragment();
		Bundle args = new Bundle();
		args.putString(DELETE_WHAT, deleteWhat);
		dialog.setTargetFragment(targetFragment, requestCode);
		dialog.setArguments(args);
		dialog.show(fragmentManager, DELETE_CONFIRM_TAG);
	}
}
