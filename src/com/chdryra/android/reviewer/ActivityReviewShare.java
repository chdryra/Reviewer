package com.chdryra.android.reviewer;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

public class ActivityReviewShare extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewShare();
	}

}
