package com.chdryra.android.reviewer;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

public class ActivityFeed extends ActivitySingleFragment {
    //new branch test
	@Override
	protected Fragment createFragment() {
		return new FragmentFeed();
	}

}
