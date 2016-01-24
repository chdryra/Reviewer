/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomRating {
    private static final Random RAND = new Random();

    //Static methods
    public static float nextRating() {
        //0 to 5, rounded to nearest 0.5.
        return Math.round(RAND.nextFloat() * 10) / 2f;
    }

    public static int nextWeight() {
        return RAND.nextInt(10);
    }
}
