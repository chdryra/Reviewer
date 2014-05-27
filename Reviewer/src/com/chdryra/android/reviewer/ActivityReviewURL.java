package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

public class ActivityReviewURL extends ActivitySingleFragment{

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewURLs();
	}

}
