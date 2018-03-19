/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault
        .Implementation.ComparitorSubject;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorSubjectTest extends TestCase {
    @SmallTest
    public void testCompare() {
        String kitten = "kitten";
        String sitting = "sitting";
        String empty = "";

        ComparitorSubject comparitor = new ComparitorSubject();
        DifferencePercentage none = new DifferencePercentage(0.0);
        DifferencePercentage all = new DifferencePercentage(1.0);
        DifferencePercentage expected = new DifferencePercentage(3.0 / 7.0);
        DifferencePercentage expectedDelta = new DifferencePercentage(3.0 / 7.0 - 0.01);

        GvSubject lhs = new GvSubject(kitten);
        GvSubject rhs = new GvSubject(kitten);
        DifferencePercentage difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvSubject(sitting);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        rhs = new GvSubject(empty);
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
    }
}
