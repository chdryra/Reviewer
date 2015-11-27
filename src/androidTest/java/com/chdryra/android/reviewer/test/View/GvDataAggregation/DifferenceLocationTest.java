package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.DataAggregation.DifferenceFloat;
import com.chdryra.android.reviewer.View.DataAggregation.DifferenceLocation;
import com.chdryra.android.reviewer.View.DataAggregation.DifferencePercentage;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 16/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceLocationTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        double nameThresh = 0.5;
        DifferencePercentage nameThreshold = new DifferencePercentage(nameThresh);
        float distThresh = 100f;
        DifferenceFloat distThreshold = new DifferenceFloat(distThresh);
        DifferenceLocation locThreshold = new DifferenceLocation(distThreshold, nameThreshold);
        Random r = new Random();
        for (int i = 0; i < 200; i++) {
            double nameDouble = r.nextDouble();
            float distFloat = r.nextFloat() * 200f;
            DifferencePercentage nameDiff = new DifferencePercentage(nameDouble);
            DifferenceFloat distDiff = new DifferenceFloat(distFloat);
            DifferenceLocation diffLocation = new DifferenceLocation(distDiff, nameDiff);
            if (nameDouble <= nameThresh && distFloat <= distThresh) {
                assertTrue(diffLocation.lessThanOrEqualTo(locThreshold));
            } else {
                assertFalse(diffLocation.lessThanOrEqualTo(locThreshold));
            }
        }
    }
}
