package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public abstract class DialogDeleteCancelDoneFragment extends DialogActionCancelDoneFragment {
	private static final int DELETE_CONFIRM = 0;
	
	private boolean mDeleteConfirmation = true;
	private String mDeleteWhat;
	
	protected abstract View createDialogUI(); 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionButtonAction(ActionType.DELETE);
		setDismissDialogOnActionClick(true);
	}
	
	protected boolean hasDataToDelete() { 
		return false;
	}
	
	protected void onDeleteButtonClick() {
	}
	
	@Override
	protected void onActionButtonClick() {
		if(hasDataToDelete())
			onDeleteButtonClick();
	}

	@Override
	protected void onLeftButtonClick() {
		if(hasDataToDelete() && mDeleteConfirmation) {
			showDeleteConfirmDialog();
			return;
		} else
			super.onLeftButtonClick();
	}
	
	protected void setDeleteWhatTitle(String deleteWhat) {
		mDeleteWhat = deleteWhat;
	}
	
	protected void setDeleteConfirmation(boolean deleteConfirmation) {
		mDeleteConfirmation = deleteConfirmation;
	}
	
	private void showDeleteConfirmDialog() {
		DialogDeleteConfirmFragment.showDeleteConfirmDialog(mDeleteWhat, DialogDeleteCancelDoneFragment.this, DELETE_CONFIRM, getFragmentManager());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {	
		switch(requestCode) {
			case DELETE_CONFIRM:
				if(ActivityResultCode.OK.equals(resultCode))
					super.onLeftButtonClick();
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
	}
}
