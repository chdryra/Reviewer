/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Implementation.DifferenceBoolean;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceBooleanTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        DifferenceBoolean thresholdTrue = new DifferenceBoolean(true);
        DifferenceBoolean thresholdFalse = new DifferenceBoolean(false);

        DifferenceBoolean boolTrue = new DifferenceBoolean(true);
        DifferenceBoolean boolFalse = new DifferenceBoolean(false);

        assertTrue(boolTrue.lessThanOrEqualTo(thresholdTrue));
        assertFalse(boolTrue.lessThanOrEqualTo(thresholdFalse));
        assertTrue(boolFalse.lessThanOrEqualTo(thresholdFalse));
        assertFalse(boolFalse.lessThanOrEqualTo(thresholdTrue));
    }
}
