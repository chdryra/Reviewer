/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

/**
 * UI Activity holding {@link FragmentSearchable}: unified search screen (currently disabled).
 */
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
