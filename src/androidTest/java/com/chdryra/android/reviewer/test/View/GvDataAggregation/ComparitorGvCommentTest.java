/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvComment;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferencePercentage;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvCommentTest extends TestCase {
    @SmallTest
    public void testCompare() {
        GvCommentList.GvComment kitten = new GvCommentList.GvComment("kitten");
        GvCommentList.GvComment sitting = new GvCommentList.GvComment("sitting");
        GvCommentList.GvComment empty = new GvCommentList.GvComment("");

        ComparitorGvComment comparitor = new ComparitorGvComment();
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
