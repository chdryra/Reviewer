/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
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
    private static final MdReviewId ID = RandomReviewId.nextId();
    private static final Random RANDOM = new Random();

    @SmallTest
    public void testGetters() {
        float score = RandomRating.nextRating();
        int weight = randomWeight();
        MdRating rating = new MdRating(ID, score, weight);
        assertEquals(score, rating.getRating());
        assertEquals(weight, rating.getRatingWeight());
        assertEquals(ID, rating.getReviewId());
    }

    @SmallTest
    public void testHasData() {
        MdRating rating = new MdRating(ID, RandomRating.nextRating(), randomWeight());
        assertTrue(rating.hasData());
    }

    @SmallTest
    public void testEqualsHash() {
        float score1 = 1f;
        int weight1 = 1;
        float score2 = 2f;
        int weight2 = 2;
        MdReviewId id2 = RandomReviewId.nextId();

        MdRating rating1 = new MdRating(ID, score1, weight1);
        MdDataUtils.testEqualsHash(rating1, new MdRating(ID, score2, weight1), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(ID, score2, weight2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(id2, score2, weight1), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(id2, score2, weight2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(ID, score1, weight2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(id2, score1, weight1), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(id2, score1, weight2), false);
        MdDataUtils.testEqualsHash(rating1, new MdRating(ID, score1, weight1), true);
    }

    private int randomWeight() {
        return RANDOM.nextInt(100);
    }
}
