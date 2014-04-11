package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

public class DialogLocationFragment extends DialogImageFragment {
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
