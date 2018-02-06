/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.startouch.test.View.Utils;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Utils.RatingFormatter;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingFormatterTest extends TestCase {
    private static final float RATING = 3.1415927f;

    @SmallTest
    public void testTwoSignificantDigits() {
        assertEquals("3.1", RatingFormatter.twoSignificantDigits(RATING));
    }

    @SmallTest
    public void testOutOfFive() {
        assertEquals("3.1/5", RatingFormatter.outOfFive(RATING));
    }

    @SmallTest
    public void testRoundToSignificant() {
        assertEquals("3", RatingFormatter.roundToSignificant(RATING, 1));
        assertEquals("3.1", RatingFormatter.roundToSignificant(RATING, 2));
        assertEquals("3.14", RatingFormatter.roundToSignificant(RATING, 3));
        assertEquals("3.142", RatingFormatter.roundToSignificant(RATING, 4));
        assertEquals("3.1416", RatingFormatter.roundToSignificant(RATING, 5));
        assertEquals("3.14159", RatingFormatter.roundToSignificant(RATING, 6));
        assertEquals("3.141593", RatingFormatter.roundToSignificant(RATING, 7));
        assertEquals("3.1415927", RatingFormatter.roundToSignificant(RATING, 8));
        //Floating point to double error. Spurious extra digits.
        //assertEquals("3.1415927", RatingFormatter.roundToSignificant(RATING, 9));
    }
}
