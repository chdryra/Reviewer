/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.SimilarityBoolean;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SimilarityBooleanTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        SimilarityBoolean thresholdTrue = new SimilarityBoolean(true);
        SimilarityBoolean thresholdFalse = new SimilarityBoolean(false);

        SimilarityBoolean boolTrue = new SimilarityBoolean(true);
        SimilarityBoolean boolFalse = new SimilarityBoolean(false);

        assertTrue(boolTrue.lessThanOrEqualTo(thresholdTrue));
        assertFalse(boolTrue.lessThanOrEqualTo(thresholdFalse));
        assertTrue(boolFalse.lessThanOrEqualTo(thresholdFalse));
        assertFalse(boolFalse.lessThanOrEqualTo(thresholdTrue));
    }
}
