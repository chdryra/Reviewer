package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Menu;

public class ReviewDefineActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new ReviewDefineFragment();
	}

}
