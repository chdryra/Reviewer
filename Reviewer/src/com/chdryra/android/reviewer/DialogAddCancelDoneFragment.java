package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

public abstract class DialogAddCancelDoneFragment extends DialogActionCancelDoneFragment {

	protected abstract View createDialogUI();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionButtonAction(ActionType.ADD);
		setDismissDialogOnActionClick(false);
	}
	
	@Override
	protected void onActionButtonClick() {
		OnAddButtonClick();
	}
	
	protected void OnAddButtonClick() {
	}
}
