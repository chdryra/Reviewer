/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.ComparitorGvFactLabel;
import com.chdryra.android.reviewer.View.GvDataAlgorithms.DifferencePercentage;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvFactLabelTest extends TestCase {
    @SmallTest
    public void testCompare() {
        String lhsLabel = "kitten";
        String rhsLabel = "sitting";
        String empty = "";
        String lhsValue = RandomString.nextWord();
        String rhsValue = RandomString.nextWord();

        ComparitorGvFactLabel comparitor = new ComparitorGvFactLabel();
        DifferencePercentage none = new DifferencePercentage(0.0);
        DifferencePercentage all = new DifferencePercentage(1.0);
        DifferencePercentage expected = new DifferencePercentage(3.0 / 7.0);
        DifferencePercentage expectedDelta = new DifferencePercentage(3.0 / 7.0 - 0.01);

        GvFactList.GvFact lhs = new GvFactList.GvFact(lhsLabel, lhsValue);
        GvFactList.GvFact rhs = new GvFactList.GvFact(lhsLabel, lhsValue);
        DifferencePercentage difference = comparitor.compare(lhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));


        rhs = new GvFactList.GvFact(lhsLabel, rhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvFactList.GvFact(rhsLabel, rhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        rhs = new GvFactList.GvFact(empty, lhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));

        rhs = new GvFactList.GvFact(empty, rhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
    }
}
