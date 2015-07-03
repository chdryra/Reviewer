/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.SimilarityPercentage;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SimilarityPercentageTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        double threshold = 0.5;
        SimilarityPercentage similarityThreshold = new SimilarityPercentage(threshold);
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            double rDouble = r.nextDouble();
            SimilarityPercentage randomFloat = new SimilarityPercentage(rDouble);
            if (rDouble > threshold) {
                assertFalse(randomFloat.lessThanOrEqualTo(similarityThreshold));
            } else {
                assertTrue(randomFloat.lessThanOrEqualTo(similarityThreshold));
            }
        }
    }
}
