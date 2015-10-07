/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 December, 2014
 */

package com.chdryra.android.reviewer.View.Utils;

import com.chdryra.android.mygenerallibrary.NumberFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingFormatter extends NumberFormatter {
    private static final int DIGITS = 2;
    private static final int MAXRATING = 5;

    //Static methods
    public static String twoSignificantDigits(float rating) {
        return roundToSignificant(rating, DIGITS);
    }

    public static String outOfFive(float rating) {
        return twoSignificantDigits(rating) + "/" + String.valueOf(MAXRATING);
    }
}
