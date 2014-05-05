package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

public class ActivityReviewURL extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewURL();
	}
}
