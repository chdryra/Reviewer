/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 December, 2014
 */

package com.chdryra.android.reviewer;

import java.text.DecimalFormat;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingFormatter {
    private static final int DIGITS    = 2;
    private static final int MAXRATING = 5;

    public static String twoSignificantDigits(float rating) {
        return roundToSignificant(rating, DIGITS);
    }

    public static String outOfFive(float rating) {
        return twoSignificantDigits(rating) + "/" + String.valueOf(MAXRATING);
    }

    public static String roundToSignificant(float rating, int digits) {
        String pattern = digits > 1 ? "0." : "0";
        while (digits-- > 1) pattern += "0";
        DecimalFormat formatter = new DecimalFormat("0");
        DecimalFormat decimalFormatter = new DecimalFormat(pattern);
        return rating % 1L > 0L ? decimalFormatter.format(rating) : formatter.format(rating);
    }
}
