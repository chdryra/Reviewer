package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

import android.support.v4.app.Fragment;

public class ActivityReviewProsCons extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewProsCons();
	}

}
