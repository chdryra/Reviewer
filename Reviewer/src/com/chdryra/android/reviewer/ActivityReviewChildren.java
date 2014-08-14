package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

import android.app.Fragment;

public class ActivityReviewChildren extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentReviewChildren();
	}

}
