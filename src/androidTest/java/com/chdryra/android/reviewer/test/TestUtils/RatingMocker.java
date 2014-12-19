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
public class RatingMocker {
    private static final Random RAND = new Random();

    public static float nextRating() {
        return Math.round(RAND.nextFloat() * 10) / 2f;
    }
}
