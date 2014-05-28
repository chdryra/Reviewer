package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

public class ActivityReviewURLs extends ActivitySingleFragment{

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewURLs();
	}

}
