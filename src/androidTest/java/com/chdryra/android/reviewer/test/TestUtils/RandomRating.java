/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomRating {
    private static final Random RAND = new Random();

    public static float nextRating() {
        //0 to 5, rounded to nearest 0.5.
        float rating = Math.round(RAND.nextFloat() * 10) / 2f;
        return Math.round(rating * 2f) / 2f;
    }
}
