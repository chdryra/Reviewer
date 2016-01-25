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
 * On: 25/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomBoolean {
    private static final Random RAND = new Random();

    public static boolean nextBoolean() {
        return RAND.nextBoolean();
    }
}
