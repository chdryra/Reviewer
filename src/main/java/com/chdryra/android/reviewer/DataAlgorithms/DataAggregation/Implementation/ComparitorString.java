/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorString implements DifferenceComparitor<String, DifferencePercentage> {

    @Override
    public DifferencePercentage compare(String lhs, String rhs) {
        boolean lhsLonger = lhs.length() > rhs.length();
        String longer = lhsLonger ? lhs : rhs;
        String shorter = lhsLonger ? rhs : lhs;
        return new DifferencePercentage(getDifference(longer, shorter));
    }

    private int getEditDistance(String longer, String shorter) {
        return StringUtils.getLevenshteinDistance(longer, shorter);
    }

    private double getDifference(String longer, String shorter) {
        return longer.length() == 0 ? 0.0 : getEditDistance(longer, shorter) / longer.length();
    }
}
