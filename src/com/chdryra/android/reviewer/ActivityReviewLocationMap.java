package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;


public class ActivityReviewLocationMap extends ActivitySingleFragment {

	private FragmentReviewLocationMap mFragment;
	
	@Override
	protected Fragment createFragment() {
		mFragment = new FragmentReviewLocationMap();
		return mFragment;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    mFragment.handleSearch();
	}
}
