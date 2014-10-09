/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

/**
 * UI Activity holding FragmentReviewBuild: editing new reviews.
 *
 * @see com.chdryra.android.reviewer.FragmentReviewBuild
 */
public class ActivityReviewBuild extends ActivitySingleFragment {

    @Override
    protected Fragment createFragment() {
        return new FragmentReviewBuild();
    }

}
