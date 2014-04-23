package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

public class DialogLocationFragment extends DialogImageFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		mImageCaption.setVisibility(View.GONE);
		return dialog;
	}
	
	@Override
	protected Bitmap getImageBitmap() {
		return mController.getMapSnapshot();
	}

	protected String getImageCaption() {
		return mController.getLocationName();
	}
	
	@Override
	protected String getCaptionHint() {
		return getResources().getString(R.string.edit_text_name_location_hint);
	}

	@Override
	protected void changeCaption() {
		mController.setLocationName(mImageCaption.getText().toString());
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.location_activity_title);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteLocation();
	}
}
