/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorShortString implements DifferenceComparitor<String, DifferencePercentage> {

    @Override
    public DifferencePercentage compare(String lhs, String rhs) {
        boolean string1Longer = lhs.length() > rhs.length();
        String longer = string1Longer ? lhs : rhs;
        String shorter = string1Longer ? rhs : lhs;

        int longerLength = longer.length();
        double pcntSame = longerLength == 0 ? 0.0 : StringUtils.getLevenshteinDistance
                (longer, shorter) / (double) longerLength;

        return new DifferencePercentage(pcntSame);
    }
}
