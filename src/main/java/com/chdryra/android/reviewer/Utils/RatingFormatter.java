/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import com.chdryra.android.mygenerallibrary.NumberUtils.NumberFormatter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.RatingDefinition;

import static com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating.MAX_RATING;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingFormatter extends NumberFormatter {
    private static final int DIGITS = 2;

    public static String upToTwoSignificantDigits(float rating) {
        RatingDefinition.throwIfOutOfRange(rating);
        return roundToSignificant(rating, DIGITS, true);
    }

    public static String twoSignificantDigits(float rating) {
        RatingDefinition.throwIfOutOfRange(rating);
        return roundToSignificant(rating, DIGITS, false);
    }

    public static String outOfFive(float rating) {
        return twoSignificantDigits(rating) + "/" + String.valueOf(MAX_RATING);
    }
}
