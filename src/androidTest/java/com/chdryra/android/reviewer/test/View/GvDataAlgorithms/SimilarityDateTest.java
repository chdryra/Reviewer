/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.SimilarityDate;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SimilarityDateTest extends TestCase {
    @SmallTest
    public void testLessThanOrEqualTo() {
        SimilarityDate day = new SimilarityDate(SimilarityDate.DateBucket.DAY);
        SimilarityDate month = new SimilarityDate(SimilarityDate.DateBucket.MONTH);
        SimilarityDate year = new SimilarityDate(SimilarityDate.DateBucket.YEAR);
        SimilarityDate none = new SimilarityDate(SimilarityDate.DateBucket.NONE);

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
