package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;

public class LocationDialogFragment extends ImageDialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		setDeleteConfirmation(getResources().getString(R.string.location_activity_title));
		return dialog;
	}
}
