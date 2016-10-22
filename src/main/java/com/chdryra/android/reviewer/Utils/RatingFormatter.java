/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import com.chdryra.android.mygenerallibrary.NumberUtils.NumberFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingFormatter extends NumberFormatter {
    private static final int DIGITS = 2;
    private static final int MAXRATING = 5;

    public static String upToTwoSignificantDigits(float rating) {
        return roundToSignificant(rating, DIGITS, true);
    }

    public static String twoSignificantDigits(float rating) {
        return roundToSignificant(rating, DIGITS, false);
    }

    public static String outOfFive(float rating) {
        return twoSignificantDigits(rating) + "/" + String.valueOf(MAXRATING);
    }
}
