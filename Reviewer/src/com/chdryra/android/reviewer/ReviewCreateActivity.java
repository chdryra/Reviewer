package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

public class ReviewCreateActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ReviewCreateFragment();
	}

}
