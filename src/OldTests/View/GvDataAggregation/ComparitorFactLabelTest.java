/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault.Implementation.ComparitorFactLabel;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorFactLabelTest extends TestCase {
    @SmallTest
    public void testCompare() {
        String lhsLabel = "kitten";
        String rhsLabel = "sitting";
        String empty = "";
        String lhsValue = RandomString.nextWord();
        String rhsValue = RandomString.nextWord();

        ComparitorFactLabel comparitor = new ComparitorFactLabel();
        DifferencePercentage none = new DifferencePercentage(0.0);
        DifferencePercentage all = new DifferencePercentage(1.0);
        DifferencePercentage expected = new DifferencePercentage(3.0 / 7.0);
        DifferencePercentage expectedDelta = new DifferencePercentage(3.0 / 7.0 - 0.01);

        GvFact lhs = new GvFact(lhsLabel, lhsValue);
        GvFact rhs = new GvFact(lhsLabel, lhsValue);
        DifferencePercentage difference = comparitor.compare(lhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));


        rhs = new GvFact(lhsLabel, rhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvFact(rhsLabel, rhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        rhs = new GvFact(empty, lhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));

        rhs = new GvFact(empty, rhsValue);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
    }
}
