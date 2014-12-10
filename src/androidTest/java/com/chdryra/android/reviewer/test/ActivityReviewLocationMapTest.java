/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.SingleLaunchActivityTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ActivityReviewLocationMap;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityReviewLocationMapTest extends
        SingleLaunchActivityTestCase<ActivityReviewLocationMap> {
    public ActivityReviewLocationMapTest() {
        super("com.chdryra.android.reviewer", ActivityReviewLocationMap.class);
    }

    @SmallTest
    public void testActivityNotNull() {
        assertNotNull(getActivity());
    }
}