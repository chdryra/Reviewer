package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

public class ActivitySearchable extends ActivitySingleFragment {

	@Override
	protected Fragment createFragment() {
		return new FragmentSearchable();
	}

	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	}
}
