/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.DifferenceDate;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceDateTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        DifferenceDate day = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceDate month = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        DifferenceDate year = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        DifferenceDate none = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);

        assertTrue(day.lessThanOrEqualTo(day));
        assertTrue(day.lessThanOrEqualTo(month));
        assertTrue(day.lessThanOrEqualTo(year));
        assertTrue(day.lessThanOrEqualTo(none));

        assertFalse(month.lessThanOrEqualTo(day));
        assertTrue(month.lessThanOrEqualTo(month));
        assertTrue(month.lessThanOrEqualTo(year));
        assertTrue(month.lessThanOrEqualTo(none));

        assertFalse(year.lessThanOrEqualTo(day));
        assertFalse(year.lessThanOrEqualTo(month));
        assertTrue(year.lessThanOrEqualTo(year));
        assertTrue(year.lessThanOrEqualTo(none));

        assertFalse(none.lessThanOrEqualTo(day));
        assertFalse(none.lessThanOrEqualTo(month));
        assertFalse(none.lessThanOrEqualTo(year));
        assertTrue(none.lessThanOrEqualTo(none));
    }
}
