/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.testutils.ExceptionTester;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewIdTest extends TestCase {

    @SmallTest
    public void testReviewId() {
        ReviewId id1 = ReviewId.generateId();
        assertNotNull(id1);
        assertTrue(id1.hasData());
        ExceptionTester.test(id1, "getHoldingReview", UnsupportedOperationException.class, null);

        ReviewId id2 = ReviewId.generateId();
        assertNotNull(id2);

        assertFalse(id1.equals(id2));

        ReviewId id3 = ReviewId.generateId(id2.toString());
        assertNotNull(id3);
        assertFalse(id1.equals(id3));
        assertTrue(id2.equals(id3));
    }
}
