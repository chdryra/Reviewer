package com.chdryra.android.reviewer;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

public class ActivityFeed extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentFeed();
	}

}
