package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.DialogAlert;

public class DialogSetImageAsBackgroundFragment extends DialogAlert {
	@Override
	protected String getAlertString() {
		return getResources().getString(R.string.dialog_set_image_as_background);
	}
	
	@Override
	protected void onRightButtonClick() {
		getNewReturnData().putExtras(getArguments());
		super.onRightButtonClick();
	}
}
