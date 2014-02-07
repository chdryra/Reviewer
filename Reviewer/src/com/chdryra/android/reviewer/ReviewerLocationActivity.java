package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockActivity;

public class ReviewerLocationActivity extends SingleFragmentActivity {
	@Override
	protected Fragment createFragment() {
		return new ReviewerLocationFragment();
	}
}
