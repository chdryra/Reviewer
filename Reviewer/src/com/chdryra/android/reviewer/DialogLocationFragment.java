package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

public class DialogLocationFragment extends DialogImageFragment {
	@Override
	protected Bitmap getImageBitmap() {
		return mReview.getLocation().getMapSnapshot();
	}

	protected String getImageCaption() {
		return mReview.getLocation().getName();
	}
	
	@Override
	protected String getCaptionHint() {
		return getResources().getString(R.string.edit_text_name_location_hint);
	}

	@Override
	protected void changeCaption() {
		mReview.getLocation().setName(mImageCaption.getText().toString());
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.location_activity_title);
	}
	
	@Override
	protected void deleteData() {
		mReview.deleteLocation();
	}
}
