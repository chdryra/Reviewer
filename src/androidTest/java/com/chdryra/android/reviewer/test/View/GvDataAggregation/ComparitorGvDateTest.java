/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.DataAggregation.ComparitorGvDate;
import com.chdryra.android.reviewer.View.DataAggregation.DifferenceDate;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;

import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvDateTest extends TestCase {
    @SmallTest
    public void testCompare() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        Calendar cal5 = Calendar.getInstance();
        cal1.set(2015, 7, 6, 11, 30);
        cal2.set(2015, 7, 6, 17, 30);
        cal3.set(2015, 7, 16, 17, 30);
        cal4.set(2015, 1, 1, 11, 30);
        cal5.set(2013, 1, 1, 11, 30);

        DifferenceDate day = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceDate month = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        DifferenceDate year = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        DifferenceDate none = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);

        ComparitorGvDate comparitor = new ComparitorGvDate();

        GvDate lhs = new GvDate(cal1.getTime());
        GvDate rhs = new GvDate(cal1.getTime());
        DifferenceDate difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(day));
        assertTrue(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(day));
        assertTrue(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvDate(cal2.getTime());
        difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(day));
        assertTrue(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(day));
        assertTrue(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvDate(cal3.getTime());
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(day));
        assertTrue(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(day));
        assertTrue(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvDate(cal4.getTime());
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(day));
        assertFalse(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(day));
        assertFalse(difference.lessThanOrEqualTo(month));
        assertTrue(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));

        rhs = new GvDate(cal5.getTime());
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(day));
        assertFalse(difference.lessThanOrEqualTo(month));
        assertFalse(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(day));
        assertFalse(difference.lessThanOrEqualTo(month));
        assertFalse(difference.lessThanOrEqualTo(year));
        assertTrue(difference.lessThanOrEqualTo(none));
    }
}
