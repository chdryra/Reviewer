/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvAuthor;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferenceBoolean;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.testutils.ExceptionTester;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvAuthorTest extends TestCase {
    @SmallTest
    public void testCompare() {
        DifferenceBoolean same = new DifferenceBoolean(true);
        DifferenceBoolean notSame = new DifferenceBoolean(false);
        ComparitorGvAuthor comparitor = new ComparitorGvAuthor();

        Author lhsAuthor = RandomAuthor.nextAuthor();
        Author rhsAuthor = RandomAuthor.nextAuthor();

        GvAuthorList.GvAuthor lhs = new GvAuthorList.GvAuthor(lhsAuthor.getName(), lhsAuthor
                .getUserId().toString());
        GvAuthorList.GvAuthor rhs = new GvAuthorList.GvAuthor(lhsAuthor.getName(), lhsAuthor
                .getUserId().toString());

        DifferenceBoolean result = comparitor.compare(lhs, rhs);
        assertTrue(result.lessThanOrEqualTo(same));
        assertFalse(result.lessThanOrEqualTo(notSame));

        result = comparitor.compare(rhs, lhs);
        assertTrue(result.lessThanOrEqualTo(same));
        assertFalse(result.lessThanOrEqualTo(notSame));

        rhs = new GvAuthorList.GvAuthor(rhsAuthor.getName(), rhsAuthor.getUserId().toString());
        result = comparitor.compare(lhs, rhs);
        assertFalse(result.lessThanOrEqualTo(same));
        assertTrue(result.lessThanOrEqualTo(notSame));
        result = comparitor.compare(rhs, lhs);
        assertFalse(result.lessThanOrEqualTo(same));
        assertTrue(result.lessThanOrEqualTo(notSame));

        rhs = new GvAuthorList.GvAuthor(lhsAuthor.getName(), rhsAuthor.getUserId().toString());
        result = comparitor.compare(lhs, rhs);
        assertFalse(result.lessThanOrEqualTo(same));
        assertTrue(result.lessThanOrEqualTo(notSame));
        result = comparitor.compare(rhs, lhs);
        assertFalse(result.lessThanOrEqualTo(same));
        assertTrue(result.lessThanOrEqualTo(notSame));

        rhs = new GvAuthorList.GvAuthor(rhsAuthor.getName(), lhsAuthor.getUserId().toString());
        GvAuthorList.GvAuthor[] params = new GvAuthorList.GvAuthor[2];
        params[0] = lhs;
        params[1] = rhs;
        ExceptionTester.test(comparitor, "compare", params, RuntimeException.class, "GvAuthors " +
                "have same ID but different names!");
    }
}
