/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.test.SingleLaunchActivityTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditLocationMap;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditLocationMapTest extends
        SingleLaunchActivityTestCase<ActivityEditLocationMap> {
    //Constructors
    public ActivityEditLocationMapTest() {
        super("com.chdryra.android.reviewer", ActivityEditLocationMap.class);
    }

    @SmallTest
    public void testActivityNotNull() {
        assertNotNull(getActivity());
    }
}
