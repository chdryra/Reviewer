package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;

public class DialogLocationFragment extends DialogImageFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mCaptionHint = getResources().getString(R.string.edit_text_name_location_hint);
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		setDeleteConfirmation(getResources().getString(R.string.location_activity_title));
		return dialog;
	}
}
