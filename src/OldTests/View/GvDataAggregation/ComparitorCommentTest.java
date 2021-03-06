/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault
        .Implementation.ComparitorComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorCommentTest extends TestCase {
    @SmallTest
    public void testCompare() {
        GvComment kitten = new GvComment("kitten");
        GvComment sitting = new GvComment("sitting");
        GvComment empty = new GvComment("");

        ComparitorComment comparitor = new ComparitorComment();
        DifferencePercentage none = new DifferencePercentage(0.0);
        DifferencePercentage all = new DifferencePercentage(1.0);
        DifferencePercentage expected = new DifferencePercentage(3.0 / 7.0);
        DifferencePercentage expectedDelta = new DifferencePercentage(3.0 / 7.0 - 0.01);

        DifferencePercentage difference = comparitor.compare(kitten, kitten);
        assertTrue(difference.lessThanOrEqualTo(none));

        difference = comparitor.compare(sitting, sitting);
        assertTrue(difference.lessThanOrEqualTo(none));

        difference = comparitor.compare(kitten, sitting);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        difference = comparitor.compare(sitting, kitten);
        assertTrue(difference.lessThanOrEqualTo(expected));
        assertFalse(difference.lessThanOrEqualTo(expectedDelta));

        difference = comparitor.compare(kitten, empty);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));

        difference = comparitor.compare(sitting, empty);
        assertTrue(difference.lessThanOrEqualTo(all));
        assertFalse(difference.lessThanOrEqualTo(none));
    }
}
