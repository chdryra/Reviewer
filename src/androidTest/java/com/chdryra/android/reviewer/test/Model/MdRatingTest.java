/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.MdRating;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdRatingTest extends TestCase {
    private static final ReviewId ID = ReviewId.generateId();

    @SmallTest
    public void testGetters() {
        float score = RandomRating.nextRating();
        MdRating rating = new MdRating(score, ID);
        assertEquals(score, rating.get());
        assertEquals(ID, rating.getReviewId());
    }

    @SmallTest
    public void testHasData() {
        float score = RandomRating.nextRating();
        MdRating rating = new MdRating(score, ID);
        assertTrue(rating.hasData());
    }

    @SmallTest
    public void testEqualsHash() {
        float score1 = 1f;
        float score2 = 2f;
        ReviewId id2 = ReviewId.generateId();

        MdRating rating1 = new MdRating(score1, ID);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score2, ID), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score1, id2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score1, ID), true);
    }
}
