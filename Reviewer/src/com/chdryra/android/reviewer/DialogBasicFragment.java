package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockDialogFragment;

public abstract class DialogBasicFragment extends SherlockDialogFragment {

	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	static final int DELETE_CONFIRM = 0;
	private static final String DELETE_CONFIRM_TAG = "DeleteConfirm";
	
	private boolean mDeleteConfirmed = false;
	private String mDeleteWhat = null;
	
	@Override
	public abstract Dialog onCreateDialog(Bundle savedInstanceState); 

	protected void setDeleteConfirmation(String deleteWhat) {
		mDeleteWhat = deleteWhat;
	}
	
	@Override
	public void onStop() {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onStop();
	}
	
	protected void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		if(resultCode == RESULT_DELETE && !mDeleteConfirmed && mDeleteWhat != null) {
			showDeleteConfirmDialog();
			return;
		}
			
		Intent i = new Intent();
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch(requestCode) {
			case DELETE_CONFIRM:
				if(resultCode == Activity.RESULT_OK) {
					mDeleteConfirmed = true;
					sendResult(RESULT_DELETE);
				}
				break;
			default:
				break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void showDeleteConfirmDialog() {
		showDeleteConfirmDialog(mDeleteWhat, DialogBasicFragment.this, getFragmentManager());
	}

	public static void showDeleteConfirmDialog(String deleteWhat, Fragment targetFragment, FragmentManager fragmentManager) {
		DialogDeleteConfirmFragment dialog = new DialogDeleteConfirmFragment();
		Bundle args = new Bundle();
		args.putString(DialogDeleteConfirmFragment.DELETE_WHAT, deleteWhat);
		dialog.setTargetFragment(targetFragment, DELETE_CONFIRM);
		dialog.setArguments(args);
		dialog.show(fragmentManager, DELETE_CONFIRM_TAG);
	}
	
	protected AlertDialog buildDialog(View v) {
		return buildDialog(v, null);
	}
	
	protected AlertDialog buildDialog(View v, String title) {
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
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
		if(title != null)
			dialog.setTitle(title);
		
		return dialog;
	}
}
