package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

public class ActivityReviewOptions extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewOptions();
	}

}
