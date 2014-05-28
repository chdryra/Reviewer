package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

import android.support.v4.app.Fragment;

public class ActivityReviewComments extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewComments();
	}

}
