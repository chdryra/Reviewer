package com.chdryra.android.reviewer;

import android.support.v4.app.Fragment;

public class ReviewOptionsActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ReviewOptionsFragment();
	}

}
