/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceFloat;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceFloatTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        float threshold = 0.5f;
        DifferenceFloat differenceThreshold = new DifferenceFloat(threshold);
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            float rFloat = r.nextFloat();
            DifferenceFloat randomFloat = new DifferenceFloat(rFloat);
            if (rFloat > threshold) {
                assertFalse(randomFloat.lessThanOrEqualTo(differenceThreshold));
            } else {
                assertTrue(randomFloat.lessThanOrEqualTo(differenceThreshold));
            }
        }
    }
}
