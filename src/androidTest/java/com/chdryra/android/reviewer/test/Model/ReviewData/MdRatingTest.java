/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.MdRating;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdRatingTest extends TestCase {
    private static final ReviewId ID = RandomReviewId.nextId();
    private static final Random RANDOM = new Random();

    @SmallTest
    public void testGetters() {
        float score = RandomRating.nextRating();
        int weight = randomWeight();
        MdRating rating = new MdRating(score, weight, ID);
        assertEquals(score, rating.getValue());
        assertEquals(weight, rating.getWeight());
        assertEquals(ID, rating.getReviewId());
    }

    @SmallTest
    public void testHasData() {
        MdRating rating = new MdRating(RandomRating.nextRating(), randomWeight(), ID);
        assertTrue(rating.hasData());
    }

    @SmallTest
    public void testEqualsHash() {
        float score1 = 1f;
        int weight1 = 1;
        float score2 = 2f;
        int weight2 = 2;
        ReviewId id2 = RandomReviewId.nextId();

        MdRating rating1 = new MdRating(score1, weight1, ID);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score2, weight1, ID), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score2, weight2, ID), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score2, weight1, id2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score2, weight2, id2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score1, weight2, ID), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score1, weight1, id2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score1, weight2, id2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(score1, weight1, ID), true);
    }

    private int randomWeight() {
        return RANDOM.nextInt(100);
    }
}
