/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.ComparitorGvReviewOverview;
import com.chdryra.android.reviewer.View.GvDataAlgorithms.DifferenceBoolean;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.ExceptionTester;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvReviewOverviewTest extends TestCase {
    @SmallTest
    public void testCompare() {
        GvReviewList.GvReviewOverview lhsReview = GvDataMocker.newReviewOverview(null);
        GvReviewList.GvReviewOverview rhsReview = GvDataMocker.newReviewOverview(null);

        ComparitorGvReviewOverview comparitor = new ComparitorGvReviewOverview();
        DifferenceBoolean same = new DifferenceBoolean(true);
        DifferenceBoolean notSame = new DifferenceBoolean(false);

        GvReviewList.GvReviewOverview lhs = new GvReviewList.GvReviewOverview(lhsReview);
        GvReviewList.GvReviewOverview rhs = new GvReviewList.GvReviewOverview(lhsReview);

        DifferenceBoolean difference = comparitor.compare(lhs, rhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertTrue(difference.lessThanOrEqualTo(same));
        assertFalse(difference.lessThanOrEqualTo(notSame));

        rhs = new GvReviewList.GvReviewOverview(rhsReview);
        difference = comparitor.compare(lhs, rhs);
        assertFalse(difference.lessThanOrEqualTo(same));
        assertTrue(difference.lessThanOrEqualTo(notSame));
        difference = comparitor.compare(rhs, lhs);
        assertFalse(difference.lessThanOrEqualTo(same));
        assertTrue(difference.lessThanOrEqualTo(notSame));

        rhs = new GvReviewList.GvReviewOverview(lhsReview.getId(), rhs.getAuthor(), rhs
                .getPublishDate(), rhs.getSubject(), rhs.getRating(), rhs.getCoverImage(), rhs
                .getHeadline(), new ArrayList<String>());
        GvReviewList.GvReviewOverview[] params = new GvReviewList.GvReviewOverview[2];
        params[0] = lhs;
        params[1] = rhs;
        ExceptionTester.test(comparitor, "compare", params, RuntimeException.class,
                "ReviewId same but other data different!");
    }
}
